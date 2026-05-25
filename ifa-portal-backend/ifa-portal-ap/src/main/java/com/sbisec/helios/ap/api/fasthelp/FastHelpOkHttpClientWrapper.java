package com.sbisec.helios.ap.api.fasthelp;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sbibits.earth.util.StringUtil;
import com.sbisec.helios.ap.api.fasthelp.exception.FastHelpException;
import com.sbisec.helios.ap.api.fasthelp.utils.FastHelpConfig;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * <p>
 * Description:FastCcsOkHttpClientWrapper
 * </p>
 *
 * @author dalian
 * @version 1.0
 * @date 3/31/2025
 */
@Component
public class FastHelpOkHttpClientWrapper {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(FastHelpOkHttpClientWrapper.class);

    /** FastHelpConfigインスタンス。クライアントの設定値を取得するために使用されます。 */
    private final FastHelpConfig fasthelpConfig;

    /**
     * コンストラクタ。FastHelpConfig を注入して初期化を行います。
     *
     * @param EvanesApiConfig クライアント設定を保持するコンフィギュレーションオブジェクト
     */
    public FastHelpOkHttpClientWrapper(FastHelpConfig fasthelpConfig) {
        this.fasthelpConfig = fasthelpConfig;
        initHttpClientManager();
    }

    /**
     * クライアントマネージャーを初期化します。
     * FastHelpConfig がnullでない場合に設定を適用します。
     */
    private void initHttpClientManager() {
        if (fasthelpConfig != null) {
            FastHelpOkHttpClientManager.getInstance()
                .setConnTimeout(fasthelpConfig.getHttpClientConnectTimeout())
                .setReadTimeout(fasthelpConfig.getHttpClientReadTimeout())
                .setWriteTimeout(fasthelpConfig.getHttpClientWriteTimeout())
                .setConnPoolTimeout(fasthelpConfig.getHttpClientConnPoolTimeout())
                .setConnPoolCachedMax(fasthelpConfig.getHttpClientConnPoolCachedMax())
                .setRetryCount(fasthelpConfig.getHttpClientRetryCount());
        }
    }

    /**
     * @param okHttpRequest
     * @return
     * @throws FastHelpException
     */
    public FastHelpOkHttpResponse post(FastHelpOkHttpRequest okHttpRequest, RequestBody reqBody) throws Exception {

        // Check the required items
        String url = okHttpRequest.getUrl();
        // url non empty check
        if (StringUtil.isNullOrEmpty(url)) {
            logger.error("Url is null or empty!");
            throw new FastHelpException("Url is null or empty!");
        }
        logger.info("Fasthelp Api Url From POST:{}", url);

        String header = "application/x-www-form-urlencoded;charset=Shift_JIS";
        logger.info("Fasthelp Api Headers From POST:{}", "Content-Type:" + header);

        if (null != reqBody) {
            Buffer buffer = new Buffer();
            reqBody.writeTo(buffer);
            MediaType mediaType = reqBody.contentType();
            Charset charset = mediaType != null ? mediaType.charset(Charset.forName("Shift_JIS")) : Charset.forName("Shift_JIS");
            String requestBodyString = buffer.readString(charset);
            logger.info("Fasthelp Api FormBody From POST:{}", requestBodyString);
        }
        // Define response
        Response response = null;
        try {
            // Build request protocols
            // Define request
            Request request = new Request.Builder()
                    .url(url)
                    .header("Content-Type", header)
                    .post(reqBody)
                    .build();

            // Synchronization request-response
            response = FastHelpOkHttpClientManager.getInstance().getClient().newCall(request).execute();
            if (null == response)
                return null;

            FastHelpOkHttpResponse okHttpResponse = new FastHelpOkHttpResponse();
            //Results of the request. true:Successful false:fail
            okHttpResponse.setSuccessful(response.isSuccessful());
            // Response code,please refer to HTTP status code for specific value.
            okHttpResponse.setStatusCode(response.code());
            // Parse the response message.
            String responseBody = new String(response.body().bytes(), "SHIFT-JIS");
            if (responseBody != null && !responseBody.isEmpty()) {
                if (responseBody.contains("\n")) {
                    responseBody = responseBody.replace("\n", "");
                }
            }
            okHttpResponse.setResponseData(responseBody);
            logger.info("Fasthelp Api Response From POST:{}", okHttpResponse.getResponsData());
            return okHttpResponse;
        } catch (Exception e) {
            logger.error("Fasthelp Exception occured.", e);
            throw new FastHelpException(e);
        } finally {
            if (null != response) {
                response.close();
            }
        }
    }
}
