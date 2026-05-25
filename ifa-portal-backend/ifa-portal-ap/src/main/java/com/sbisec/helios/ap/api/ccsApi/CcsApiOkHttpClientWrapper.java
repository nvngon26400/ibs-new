package com.sbisec.helios.ap.api.ccsApi;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sbibits.earth.util.StringUtil;
import com.sbisec.helios.ap.api.ccsApi.exception.CcsApiException;
import com.sbisec.helios.ap.api.ccsApi.util.CcsApiConfig;
import com.sbisec.helios.ap.api.ccsApi.util.CcsApiUtil;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * CCS API に HTTP リクエストを送信するためのクライアントラッパー。
 * 
 * シングルトンパターンで実装されており、HTTP クライアントの設定やリクエスト処理を一元管理します。
 */
@Component
public class CcsApiOkHttpClientWrapper {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(CcsApiOkHttpClientWrapper.class);

    /** CcsApiConfigインスタンス。クライアントの設定値を取得するために使用されます。 */
    private final CcsApiConfig ccsApiConfig;

    /**
     * コンストラクタ。CcsApiConfig を注入して初期化を行います。
     *
     * @param CcsApiConfig クライアント設定を保持するコンフィギュレーションオブジェクト
     */
    public CcsApiOkHttpClientWrapper(CcsApiConfig ccsApiConfig) {
        this.ccsApiConfig = ccsApiConfig;
        initHttpClientManager();
    }

    /**
     * クライアントマネージャーを初期化します。
     * CcsApiConfig がnullでない場合に設定を適用します。
     */
    private void initHttpClientManager() {
        if (ccsApiConfig != null) {
            CcsApiOkHttpClientManager.getInstance()
                .setConnTimeout(ccsApiConfig.getHttpClientConnectTimeout())
                .setReadTimeout(ccsApiConfig.getHttpClientReadTimeout())
                .setWriteTimeout(ccsApiConfig.getHttpClientWriteTimeout())
                .setConnPoolTimeout(ccsApiConfig.getHttpClientConnPoolTimeout())
                .setConnPoolCachedMax(ccsApiConfig.getHttpClientConnPoolCachedMax())
                .setRetryCount(ccsApiConfig.getHttpClientRetryCount());
        }
    }

    /**
     * 指定されたリクエストオブジェクトとリクエストボディを使用して、POST リクエストを送信します。
     * 
     * @param okHttpRequest リクエスト情報（URLなど）
     * @param reqBody       リクエストボディ
     * @return              レスポンス情報
     * @throws Exception    リクエスト処理中にエラーが発生した場合
     */
    public CcsApiOkHttpResponse post(CcsApiOkHttpRequest okHttpRequest, RequestBody reqBody) throws Exception {
        // 必須項目チェック
        String url = okHttpRequest.getUrl();
        // URL が空の場合のチェック
        if (StringUtil.isNullOrEmpty(url)) {
            logger.error("Url is null or empty!");
            throw new CcsApiException("Url is null or empty!");
        }
        logger.info("CCS Api Url From POST:{}", url);

        String header = "application/x-www-form-urlencoded;charset="+CcsApiUtil.CHARSET_SJIS;
        logger.info("CCS Api Headers From POST:{}", "Content-Type:" + header);

        if (null != reqBody) {
            Buffer buffer = new Buffer();
            reqBody.writeTo(buffer);
            MediaType mediaType = reqBody.contentType();
            Charset charset = mediaType != null ? mediaType.charset(Charset.forName(CcsApiUtil.CHARSET_SJIS)) : Charset.forName(CcsApiUtil.CHARSET_SJIS);
            String requestBodyString = buffer.readString(charset);
            logger.info("CCS Api FormBody From POST:{}", requestBodyString);
        }
        // レスポンス定義
        Response response = null;
        try {
            // レスポンス定義
            Request request = new Request.Builder()
                    .url(url)
                    .header("Content-Type", header)
                    .post(reqBody)
                    .build();
            // リクエスト実行
            response = CcsApiOkHttpClientManager.getInstance().getClient().newCall(request).execute();
            if (null == response)
                return null;
            CcsApiOkHttpResponse okHttpResponse = new CcsApiOkHttpResponse();
            // リクエスト結果（true:成功 / false:失敗）
            okHttpResponse.setSuccess(response.isSuccessful());
            // ステータスコード（HTTP ステータスコードを参照）
            okHttpResponse.setStsCode(response.code());
            // レスポンスデータを解析
            Charset charset = Charset.forName(CcsApiUtil.CHARSET_MS932);
            String responseBody = new String(response.body().bytes(), charset);
            if (responseBody != null && !responseBody.isEmpty()) {
                if (responseBody.startsWith("<!DOCTYPE")) {
                    logger.error("CCS Api response is HTML, which indicates an error or login page.");
                    throw new CcsApiException("API response is not valid. It seems to be a login page or error page.");
                }
            }
            okHttpResponse.setResData(responseBody);
            logger.info("CCS Api Response From POST:{}", okHttpResponse.getResData());
            return okHttpResponse;
        } catch (Exception e) {
            logger.error("CCS Api Exception occured.", e);
            throw new CcsApiException(e);
        } finally {
            if (null != response) {
                response.close();
            }
        }
    }
}
