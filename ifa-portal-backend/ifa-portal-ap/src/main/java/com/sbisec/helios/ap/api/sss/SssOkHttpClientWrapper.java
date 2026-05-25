package com.sbisec.helios.ap.api.sss;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbibits.earth.util.StringUtil;
import com.sbibits.earth.util.json.JsonConverter;
import com.sbisec.helios.ap.api.sss.util.SssConfig;
import com.sbisec.helios.ap.api.sss.util.SssConnectionException;

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
 * @author toma.hayashi
 * @version 1.0
 * @date 11/08/2023
 */
@Component
public class SssOkHttpClientWrapper implements Cloneable {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(SssOkHttpClientWrapper.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	private final String ENCODE = "UTF-8";

	/** JSON変換クラスのインスタンス */
	protected JsonConverter jc = JsonConverter.getInstance();

    /** SssConfigインスタンス。クライアントの設定値を取得するために使用されます。 */
    private final SssConfig sssConfig;

    /**
     * コンストラクタ。SssConfig を注入して初期化を行います。
     *
     * @param SssConfig クライアント設定を保持するコンフィギュレーションオブジェクト
     */
    public SssOkHttpClientWrapper(SssConfig sssConfig) {
        this.sssConfig = sssConfig;
        initHttpClientManager();
    }

    /**
     * クライアントマネージャーを初期化します。
     * SssConfig がnullでない場合に設定を適用します。
     */
    private void initHttpClientManager() {
        if (sssConfig != null) {
            SssOkHttpClientManager.getInstance()
                .setConnTimeout(sssConfig.getHttpClientConnectTimeout())
                .setReadTimeout(sssConfig.getHttpClientReadTimeout())
                .setWriteTimeout(sssConfig.getHttpClientWriteTimeout())
                .setConnPoolTimeout(sssConfig.getHttpClientConnPoolTimeout())
                .setConnPoolCachedMax(sssConfig.getHttpClientConnPoolCachedMax())
                .setRetryCount(sssConfig.getHttpClientRetryCount());
        }
    }

	/**
	 * @param sssOkHttpRequest
	 * @return
	 * @throws SssConnectionException
	 */
	@SuppressWarnings("unchecked")
    public SssOkHttpResponse get(SssOkHttpRequest sssOkHttpRequest) throws SssConnectionException {
		// Check the required items
		String url = sssOkHttpRequest.getUrl();
		// url non empty check
		if (StringUtil.isNullOrEmpty(url)) {
			logger.error("Url is null or empty!");
			throw new SssConnectionException("Url is null or empty!");
		}
		logger.info("Sss Api Url From GET:{}" , url);

		// Create a URL wrapper class
		HttpUrl.Builder urlBuilder = HttpUrl.Companion.parse(url).newBuilder();

		// Add parameters if exists
		if (null != sssOkHttpRequest.getParameters() && !sssOkHttpRequest.getParameters().isEmpty()) {
			try {
				for (String key : sssOkHttpRequest.getParameters().keySet()) {

					Object obj = sssOkHttpRequest.getParameters().get(key);
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
				logger.info("Sss Api Parameters From GET:{}" , sssOkHttpRequest.getParameters());
			} catch (Exception e) {
				logger.error("Sss Exception occured.", e.getMessage());
				logger.info("Sss Exception occured.", e);
				throw new SssConnectionException(e);
			}
		}

		// Define response
		Response response = null;
		try {
			// Build request protocols
			Request request = new Request.Builder().url(urlBuilder.build()).build();
			// Synchronization request-response
			response = SssOkHttpClientManager.getInstance().getClient().newCall(request).execute();
			if (null == response)
				return null;

			SssOkHttpResponse okHttpResponse = new SssOkHttpResponse();
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
			logger.error("Sss Exception occured.", e.getMessage());
			logger.info("Sss Exception occured.", e);
			throw new SssConnectionException(e);
		} finally {
			if (null != response) {
				response.close();
			}
		}
	}

	/**
	 * @param sssOkHttpRequest
	 * @return SssOkHttpResponse
	 * @throws SssConnectionException
	 */
	public SssOkHttpResponse post(SssOkHttpRequest sssOkHttpRequest) throws SssConnectionException {

		// Check the required items
		String url = sssOkHttpRequest.getUrl();
		// url non empty check
		if (StringUtil.isNullOrEmpty(url)) {
			logger.error("Url is null or empty!");
			throw new SssConnectionException("Url is null or empty!");
		}
		logger.info("Sss Api Url From POST:{}" , url);
		// contentType non empty check
		MediaType contentType = sssOkHttpRequest.getContentType();
		if (null == contentType) {
			logger.error("ContentType is empty!");
			throw new SssConnectionException("ContentType is empty!");
		}


		// Serialize request message
		String jsonBody = "";
		try {
			if (null != sssOkHttpRequest.getParameters()) {
				jsonBody = objectMapper.writeValueAsString(sssOkHttpRequest.getParameters());
			}
			logger.info("Sss Api Parameters From POST:{}" , jc.toString(sssOkHttpRequest.getParameters()));
		} catch (Exception e) {
			logger.error("Sss Exception occured.", e.getMessage());
			logger.info("Sss Exception occured.", e);
			throw new SssConnectionException(e);
		}

		// Define response
		Response response = null;
		try {
			// Build request protocols
			// Define request
			Request request = new Request.Builder().url(url)
					.post(RequestBody.Companion.create(jsonBody, contentType)).build();
			// Synchronization request-response
			response = SssOkHttpClientManager.getInstance().getClient().newCall(request).execute();
			if (null == response)
				return null;

			SssOkHttpResponse okHttpResponse = new SssOkHttpResponse();
			/*
			 * Results of the request. true:Successful false:fail
			 */
			okHttpResponse.setSuccessful(response.isSuccessful());
			// Response code,please refer to HTTP status code for specific value.
			okHttpResponse.setStatusCode(response.code());
			// Parse the response message.
			okHttpResponse.setResponseData(new String(response.body().bytes(), this.ENCODE));

			logger.info("Sss Api Response From POST:{}", okHttpResponse.getResponsData());

			return okHttpResponse;

		} catch (Exception e) {
			logger.error("Sss Exception occured.", e.getMessage());
			logger.info("Sss Exception occured.", e);
			throw new SssConnectionException(e);
		} finally {
			if (null != response) {
				response.close();
			}
		}
	}
}
