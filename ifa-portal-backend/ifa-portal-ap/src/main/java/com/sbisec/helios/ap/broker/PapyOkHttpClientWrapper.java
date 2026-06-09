package com.sbisec.helios.ap.broker;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sbibits.earth.util.StringUtil;
import com.sbisec.helios.ap.broker.util.PapyConfig;
import com.sbisec.helios.ap.broker.util.PapyConnectionException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * <p>
 * Description:OkHttpClientWrapper
 * </p>
 *
 * @author katsuhiko.kagoshima
 * @date 12/23/2022
 */
@Component
public class PapyOkHttpClientWrapper {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(PapyOkHttpClientWrapper.class);

    private final String ENCODE = "UTF-8";

    /** PapyConfigインスタンス。クライアントの設定値を取得するために使用されます。 */
    private final PapyConfig papyConfig;

    /**
     * コンストラクタ。PapyConfig を注入して初期化を行います。
     *
     * @param PapyConfig クライアント設定を保持するコンフィギュレーションオブジェクト
     */
    public PapyOkHttpClientWrapper(PapyConfig papyConfig) {
        this.papyConfig = papyConfig;
        initHttpClientManager();
    }

    /**
     * クライアントマネージャーを初期化します。
     * PapyConfig がnullでない場合に設定を適用します。
     */
    private void initHttpClientManager() {
        if (papyConfig != null) {
            PapyOkHttpClientManager.getInstance()
                .setConnTimeout(papyConfig.getHttpClientConnectTimeout())
                .setReadTimeout(papyConfig.getHttpClientReadTimeout())
                .setWriteTimeout(papyConfig.getHttpClientWriteTimeout())
                .setConnPoolTimeout(papyConfig.getHttpClientConnPoolTimeout())
                .setConnPoolCachedMax(papyConfig.getHttpClientConnPoolCachedMax())
                .setRetryCount(papyConfig.getHttpClientRetryCount());
        }
    }

    /**
     * @param okHttpRequest
     * @return
     * @throws PapyConnectionException
     */
    public PapyOkHttpResponse post(PapyOkHttpRequest okHttpRequest) throws PapyConnectionException {
        
        // Check the required items
        String url = okHttpRequest.getUrl();
        // url non empty check
        if (StringUtil.isNullOrEmpty(url)) {
            logger.error("Url is null or empty!");
            throw new PapyConnectionException("Url is null or empty!");
        }
        logger.info("Papy Api Url From POST:{}", url);
        // contentType non empty check
        MediaType contentType = okHttpRequest.getContentType();
        if (null == contentType) {
            logger.error("ContentType is empty!");
            throw new PapyConnectionException("ContentType is empty!");
        }
        
        // Add headers if exists
        Headers.Builder headers = new Headers.Builder();
        if (null != okHttpRequest.getHeaders() && !okHttpRequest.getHeaders().isEmpty()) {
            for (String key : okHttpRequest.getHeaders().keySet()) {
                String value = String.valueOf(okHttpRequest.getHeaders().get(key));
                headers.add(key, value);
            }
            logger.info("Papy Api Headers From POST:{}", okHttpRequest.getHeaders());
        }
        
        // Serialize request message
        RequestBody requestBody = null;
        try {
            if (null != okHttpRequest.getParameters()) {
                requestBody = createEncodedUrlRequest(okHttpRequest.getParameters());
            }
            logger.info("Papy Api Parameters From POST:{}", okHttpRequest.getParameters());
        } catch (Exception e) {
            logger.info("Papy Exception occured.:", e);
            throw new PapyConnectionException();
        }
        
        // Define response
        Response response = null;
        try {
            // Build request protocols
            // Define request
            Request request = new Request.Builder().url(url).headers(headers.build()).post(requestBody).build();
            // Synchronization request-response
            response = PapyOkHttpClientManager.getInstance().getClient().newCall(request).execute();
            if (null == response)
                return null;
            
            PapyOkHttpResponse okHttpResponse = new PapyOkHttpResponse();
            /*
             * Results of the request. true:Successful false:fail
             */
            okHttpResponse.setSuccessful(response.isSuccessful());
            // Response code,please refer to HTTP status code for specific value.
            okHttpResponse.setStatusCode(response.code());
            // Parse the response message.
            okHttpResponse.setResponseData(new String(response.body().bytes(), this.ENCODE));
            
            return okHttpResponse;
            
        } catch (Exception e) {
            logger.info("Papy Exception occured.");
            throw new PapyConnectionException(e);
        } finally {
            if (null != response) {
                response.close();
            }
        }
    }

    /**
     * x-www-form-unlencoded形式のリクエストボディを生成する。
     * parameters
     * @return
     */
    private RequestBody createEncodedUrlRequest(Map<String, Object> parameters) {
        
        // 各パラメータをx-www-form-urlencoded形式に変換するオブジェクトの呼出し 
        FormBody.Builder formBuilder = new FormBody.Builder();
        // 各パラメータを取得し、FormBody.Builderに設定する。
        for (String key : parameters.keySet()) {
            String value = String.valueOf(parameters.get(key));
            formBuilder.add(key, value);
        }
        
        return formBuilder.build();
    }
}
