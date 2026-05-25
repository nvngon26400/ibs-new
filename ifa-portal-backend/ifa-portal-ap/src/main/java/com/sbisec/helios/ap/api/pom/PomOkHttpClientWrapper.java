package com.sbisec.helios.ap.api.pom;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbibits.earth.util.StringUtil;
import com.sbisec.helios.ap.api.pom.utils.PomConfig;
import com.sbisec.helios.ap.api.pom.utils.PomException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * <p>
 * Description:OkHttpClientWrapper
 * </p>
 *
 * @author shuchen.xin
 * @version 1.0
 * @date 5/20/2021
 */
@Component
public class PomOkHttpClientWrapper {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(PomOkHttpClientWrapper.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String ENCODE = "UTF-8";

    /** PomConfigインスタンス。クライアントの設定値を取得するために使用されます。 */
    private final PomConfig pomConfig;

    /**
     * コンストラクタ。PomConfig を注入して初期化を行います。
     *
     * @param PomConfig クライアント設定を保持するコンフィギュレーションオブジェクト
     */
    public PomOkHttpClientWrapper(PomConfig pomConfig) {
        this.pomConfig = pomConfig;
        initHttpClientManager();
    }

    /**
     * クライアントマネージャーを初期化します。
     * SafeConfig がnullでない場合に設定を適用します。
     */
    private void initHttpClientManager() {
        if (pomConfig != null) {
            PomOkHttpClientManager.getInstance()
                .setConnTimeout(pomConfig.getHttpClientConnectTimeout())
                .setReadTimeout(pomConfig.getHttpClientReadTimeout())
                .setWriteTimeout(pomConfig.getHttpClientWriteTimeout())
                .setConnPoolTimeout(pomConfig.getHttpClientConnPoolTimeout())
                .setConnPoolCachedMax(pomConfig.getHttpClientConnPoolCachedMax())
                .setRetryCount(pomConfig.getHttpClientRetryCount());
        }
    }

    /**
     * @param okHttpRequest 要求
     * @return 応答
     * @throws PomException システム例外
     */
    @SuppressWarnings("unchecked")
    public PomOkHttpResponse get(PomOkHttpRequest okHttpRequest) throws PomException {
        
        // Check the required items
        String url = okHttpRequest.getUrl();
        // url non empty check
        if (StringUtil.isNullOrEmpty(url)) {
            LOGGER.error("Url is null or empty!");
            throw new PomException("Url is null or empty!");
        }
        LOGGER.info("Pom Api Url From GET:{}", url);
        
        // Create a URL wrapper class
        HttpUrl.Builder urlBuilder = Optional.ofNullable(HttpUrl.Companion.parse(url)) //
                .map(HttpUrl::newBuilder) //
                .orElse(null);
        if (Objects.isNull(urlBuilder)) {
            LOGGER.error("Failed to build url!");
            throw new PomException("Failed to build url!");
            
        }
        
        // Add parameters if exists
        if (null != okHttpRequest.getParameters() && !okHttpRequest.getParameters().isEmpty()) {
            try {
                for (String key : okHttpRequest.getParameters().keySet()) {
                    
                    Object obj = okHttpRequest.getParameters().get(key);
                    if (obj instanceof List) {
                        // パラメータが配列の場合、
                        for (String str : (List<String>) obj) {
                            // 例：/information/market_price/prices?countryCode=US&rics=MCD&rics=BBD
                            if (!StringUtil.isNullOrEmpty(str)) {
                                urlBuilder.addQueryParameter(key, str);
                            }
                        }
                    } else {
                        if (obj != null && !StringUtil.isNullOrEmpty(obj.toString())) {
                            urlBuilder.addQueryParameter(key, obj.toString());
                        }
                    }
                }
                LOGGER.info("Pom Api Parameters From GET:{}", okHttpRequest.getParameters());
            } catch (Exception e) {
                LOGGER.error("Pom Exception occured.", e.getMessage());
                LOGGER.info("Pom Exception occured.", e);
                throw new PomException(e);
            }
        }
        
        // Add headers if exists
        Headers.Builder headers = new Headers.Builder();
        if (null != okHttpRequest.getHeaders() && !okHttpRequest.getHeaders().isEmpty()) {
            for (String key : okHttpRequest.getHeaders().keySet()) {
                String value = String.valueOf(okHttpRequest.getHeaders().get(key));
                headers.add(key, value);
            }
            LOGGER.info("Pom Api Headers From GET:{}", okHttpRequest.getHeaders());
        }
        
        // Define response
        Response response = null;
        try {
            // Build request protocols
            Request request = new Request.Builder().url(urlBuilder.build()).headers(headers.build()).build();
            // Synchronization request-response
            response = PomOkHttpClientManager.getInstance().getClient().newCall(request).execute();
            if (null == response) {
                return null;
            }
            PomOkHttpResponse okHttpResponse = new PomOkHttpResponse();
            /*
             * Results of the request. true:Successful false:fail
             */
            okHttpResponse.setSuccessful(response.isSuccessful());
            // Response code,please refer to HTTP status code for specific value.
            okHttpResponse.setStatusCode(response.code());
            // Parse the response message.
            var body = response.body();
            if (Objects.isNull(body)) {
                LOGGER.error("body is null.");
                throw new PomException("body is null.");
            }
            okHttpResponse.setResponseData(new String(body.bytes(), ENCODE));
            return okHttpResponse;
            
        } catch (Exception e) {
            LOGGER.error("Pom Exception occured.", e.getMessage());
            LOGGER.info("Pom Exception occured.", e);
            throw new PomException(e);
        } finally {
            if (null != response) {
                response.close();
            }
        }
    }
    
    /**
     * @param okHttpRequest 要求
     * @return 応答
     * @throws PomException システム例外
     */
    public PomOkHttpResponse post(PomOkHttpRequest okHttpRequest) throws PomException {
        
        // Check the required items
        String url = okHttpRequest.getUrl();
        // url non empty check
        if (StringUtil.isNullOrEmpty(url)) {
            LOGGER.error("Url is null or empty!");
            throw new PomException("Url is null or empty!");
        }
        LOGGER.info("Pom Api Url From POST:{}", url);
        // contentType non empty check
        MediaType contentType = okHttpRequest.getContentType();
        if (null == contentType) {
            LOGGER.error("ContentType is empty!");
            throw new PomException("ContentType is empty!");
        }
        
        // Add headers if exists
        Headers.Builder headers = new Headers.Builder();
        if (null != okHttpRequest.getHeaders() && !okHttpRequest.getHeaders().isEmpty()) {
            for (String key : okHttpRequest.getHeaders().keySet()) {
                String value = String.valueOf(okHttpRequest.getHeaders().get(key));
                headers.add(key, value);
            }
            LOGGER.info("Pom Api Headers From POST:{}", okHttpRequest.getHeaders());
        }
        
        // Serialize request message
        String jsonBody = "";
        try {
            if (null != okHttpRequest.getParameters()) {
                jsonBody = objectMapper.writeValueAsString(okHttpRequest.getParameters());
            }
            LOGGER.info("Pom Api Parameters From POST:{}", jsonBody);
        } catch (Exception e) {
            LOGGER.error("Pom Exception occured.", e.getMessage());
            LOGGER.info("Pom Exception occured.", e);
            throw new PomException(e);
        }
        
        // Define response
        Response response = null;
        try {
            // Build request protocols
            // Define request
            Request request = new Request.Builder().url(url).headers(headers.build())
                    .post(RequestBody.Companion.create(jsonBody, contentType)).build();
            // Synchronization request-response
            response = PomOkHttpClientManager.getInstance().getClient().newCall(request).execute();
            if (null == response) {
                return null;
            }
            PomOkHttpResponse okHttpResponse = new PomOkHttpResponse();
            /*
             * Results of the request. true:Successful false:fail
             */
            okHttpResponse.setSuccessful(response.isSuccessful());
            // Response code,please refer to HTTP status code for specific value.
            okHttpResponse.setStatusCode(response.code());
            // Parse the response message.
            var body = response.body();
            if (Objects.isNull(body)) {
                LOGGER.error("body is null.");
                throw new PomException("body is null.");
            }
            okHttpResponse.setResponseData(new String(body.bytes(), ENCODE));
            
            LOGGER.info("Pom Api Response From POST:{}", okHttpResponse.getResponsData());
            
            return okHttpResponse;
            
        } catch (Exception e) {
            LOGGER.error("Pom Exception occured.", e.getMessage());
            LOGGER.info("Pom Exception occured.", e);
            throw new PomException(e);
        } finally {
            if (null != response) {
                response.close();
            }
        }
    }
    
    /**
     * Delete processing is performed.
     *
     * @param okHttpRequest 要求
     * @return 応答
     * @throws PomException システム例外
     */
    public PomOkHttpResponse delete(PomOkHttpRequest okHttpRequest) throws PomException {
        
        String url = okHttpRequest.getUrl();
        // url non empty check
        if (StringUtil.isNullOrEmpty(url)) {
            LOGGER.error("Url is null or empty!");
            throw new PomException("Url is null or empty!");
        }
        LOGGER.info("Pom Api Url From DELETE:{}", url);
        // contentType non empty check
        MediaType contentType = okHttpRequest.getContentType();
        if (null == contentType) {
            LOGGER.error("ContentType is empty!");
            throw new PomException("ContentType is empty!");
        }
        
        // Add headers if exists
        Headers.Builder headers = new Headers.Builder();
        if (null != okHttpRequest.getHeaders() && !okHttpRequest.getHeaders().isEmpty()) {
            for (String key : okHttpRequest.getHeaders().keySet()) {
                String value = String.valueOf(okHttpRequest.getHeaders().get(key));
                headers.add(key, value);
            }
            LOGGER.info("Pom Api Headers From DELETE:{}", okHttpRequest.getHeaders());
        }
        
        // Serialize request message
        String jsonBody = "";
        try {
            // Serialize only if the request parameter exists
            if (null != okHttpRequest.getParameters()) {
                jsonBody = objectMapper.writeValueAsString(okHttpRequest.getParameters());
            }
            LOGGER.info("Pom Api Parameters From DELETE:{}", okHttpRequest.getParameters());
        } catch (Exception e) {
            LOGGER.error("Pom Exception occured.", e.getMessage());
            LOGGER.info("Pom Exception occured.", e);
            throw new PomException(e);
        }
        
        // Define response
        Response response = null;
        try {
            // Build request protocols
            Request request = null;
            if (StringUtil.isNullOrEmpty(jsonBody)) {
                // Delete method is used when there are no parameters
                request = new Request.Builder().url(url).headers(headers.build()).delete().build();
            } else {
                // Delete(requestbody) method is used when there are no parameters
                request = new Request.Builder().url(url).headers(headers.build())
                        .delete(RequestBody.Companion.create(jsonBody, contentType)).build();
            }
            
            response = PomOkHttpClientManager.getInstance().getClient().newCall(request).execute();
            if (null == response) {
                return null;
            }
            
            // Encapsulate okhttpresponse according to the result of response
            PomOkHttpResponse okHttpResponse = new PomOkHttpResponse();
            // Results of the request. true:Successful false:fail
            okHttpResponse.setSuccessful(response.isSuccessful());
            // Response code,please refer to HTTP status code for specific value.
            okHttpResponse.setStatusCode(response.code());
            // Parse the response message.
            var body = response.body();
            if (Objects.isNull(body)) {
                LOGGER.error("body is null.");
                throw new PomException("body is null.");
            }
            okHttpResponse.setResponseData(new String(body.bytes(), ENCODE));
            LOGGER.info("Pom Api Response From DELETE:{}", okHttpResponse.getResponsData());
            
            return okHttpResponse;
        } catch (Exception e) {
            LOGGER.error("Pom Exception occured.", e.getMessage());
            LOGGER.info("Pom Exception occured.", e);
            throw new PomException(e);
        } finally {
            if (null != response) {
                response.close();
            }
        }
        
    }
    
    /**
     * Update processing is performed.
     *
     * @param okHttpRequest 要求
     * @return 応答
     * @throws PomException システム例外
     */
    public PomOkHttpResponse put(PomOkHttpRequest okHttpRequest) throws PomException {
        
        String url = okHttpRequest.getUrl();
        // url non empty check
        if (StringUtil.isNullOrEmpty(url)) {
            LOGGER.error("Url is null or empty!");
            throw new PomException("Url is null or empty!");
        }
        LOGGER.info("Pom Api Url From PUT:{}", url);
        // contentType non empty check
        MediaType contentType = okHttpRequest.getContentType();
        if (null == contentType) {
            LOGGER.error("ContentType is empty!");
            throw new PomException("ContentType is empty!");
        }
        
        // Add headers if exists
        Headers.Builder headers = new Headers.Builder();
        if (null != okHttpRequest.getHeaders() && !okHttpRequest.getHeaders().isEmpty()) {
            for (String key : okHttpRequest.getHeaders().keySet()) {
                String value = String.valueOf(okHttpRequest.getHeaders().get(key));
                headers.add(key, value);
            }
            LOGGER.info("Pom Api Headers From PUT:{}", okHttpRequest.getHeaders());
        }
        
        // Serialize request message
        String jsonBody = "";
        try {
            // Serialize only if the request parameter exists
            if (null != okHttpRequest.getParameters()) {
                jsonBody = objectMapper.writeValueAsString(okHttpRequest.getParameters());
            }
            LOGGER.info("Pom Api Parameters From PUT:{}", jsonBody);
        } catch (Exception e) {
            LOGGER.error("Pom Exception occured.", e.getMessage());
            LOGGER.info("Pom Exception occured.", e);
            throw new PomException(e);
        }
        
        // Define response
        Response response = null;
        try {
            // Build request protocols
            // put(requestbody) method is used when there are no parameters
            Request request = new Request.Builder().url(url).headers(headers.build())
                    .put(RequestBody.Companion.create(jsonBody, contentType)).build();
            response = PomOkHttpClientManager.getInstance().getClient().newCall(request).execute();
            if (null == response) {
                return null;
            }
            
            // Encapsulate okhttpresponse according to the result of response
            PomOkHttpResponse okHttpResponse = new PomOkHttpResponse();
            // Results of the request. true:Successful false:fail
            okHttpResponse.setSuccessful(response.isSuccessful());
            // Response code,please refer to HTTP status code for specific value.
            okHttpResponse.setStatusCode(response.code());
            // Parse the response message.
            var body = response.body();
            if (Objects.isNull(body)) {
                LOGGER.error("body is null.");
                throw new PomException("body is null.");
            }
            okHttpResponse.setResponseData(new String(body.bytes(), ENCODE));
            LOGGER.info("Pom Api Response From PUT:{}", okHttpResponse.getResponsData());
            return okHttpResponse;
        } catch (Exception e) {
            LOGGER.error("Pom Exception occured.", e.getMessage());
            LOGGER.info("Pom Exception occured.", e);
            throw new PomException(e);
        } finally {
            if (null != response) {
                response.close();
            }
        }
    }
}
