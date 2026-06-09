package com.sbisec.helios.ap.api.athena;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbibits.earth.util.StringUtil;
import com.sbibits.earth.util.json.JsonConverter;
import com.sbisec.helios.ap.api.athena.utils.AthenaConfig;
import com.sbisec.helios.ap.api.athena.utils.AthenaException;

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
public class OkHttpClientWrapper {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(OkHttpClientWrapper.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    private final String ENCODE = "UTF-8";
    
    /** JSON変換クラスのインスタンス */
    protected JsonConverter jc = JsonConverter.getInstance();

    private final PathMatcher pathMatcher = new AntPathMatcher();

    /** print response log whitelist urls */
    /**
     * 外国株式注文一覧取得,外国株式銘柄検索, 外国株式信用建玉明細一覧を取得, 外国株式信用売建可能銘柄一覧取得, 外貨建商品保有証券一覧取得,
     * マーケット価格取得API, マーケット価格ハッシュ取得API
     */
    private String[] whiteListURLs = { "/fstock/order/orders", "/fstock/securities",
            "/account/balance/positions:listDetails", "/fstock/securities/shortable_stocks",
            "/account/balance/securities_balances", "/information/market_price/prices",
            "/information/market_price/countries/US/price_hashes" };

    
    /** AthenaConfigインスタンス。クライアントの設定値を取得するために使用されます。 */
    private final AthenaConfig athenaConfig;

    /**
     * コンストラクタ。AthenaConfig を注入して初期化を行います。
     *
     * @param athenaConfig クライアント設定を保持するコンフィギュレーションオブジェクト
     */
    public OkHttpClientWrapper(AthenaConfig athenaConfig) {
        this.athenaConfig = athenaConfig;
        initHttpClientManager();
    }

    /**
     * クライアントマネージャーを初期化します。
     * AthenaConfig がnullでない場合に設定を適用します。
     */
	private void initHttpClientManager() {
        if (athenaConfig != null) {
            OkHttpClientManager.getInstance()
                .setConnTimeout(athenaConfig.getHttpClientConnectTimeout())
                .setReadTimeout(athenaConfig.getHttpClientReadTimeout())
                .setWriteTimeout(athenaConfig.getHttpClientWriteTimeout())
                .setConnPoolTimeout(athenaConfig.getHttpClientConnPoolTimeout())
                .setConnPoolCachedMax(athenaConfig.getHttpClientConnPoolCachedMax())
                .setRetryCount(athenaConfig.getHttpClientRetryCount());
        }
    }

    /**
     * Determine whether it is a white list
     * @param url
     * @return boolean
     */
    private boolean isWhiteURL(String url) {
        for (String whiteURL : whiteListURLs) {
            whiteURL = athenaConfig.getHostProtocol() + "://" + athenaConfig.getHostIp() + ":"
                    + athenaConfig.getHostPort() + whiteURL;
            if (pathMatcher.match(url, whiteURL)) {
                return true;
            }
        }
        return false;
    }

	/**
	 * @param okHttpRequest
	 * @return
	 * @throws AthenaException
	 */
	@SuppressWarnings("unchecked")
    public OkHttpResponse get(OkHttpRequest okHttpRequest) throws AthenaException {
		// Check the required items
		String url = okHttpRequest.getUrl();
		// url non empty check
		if (StringUtil.isNullOrEmpty(url)) {
			logger.error("Url is null or empty!");
			throw new AthenaException("Url is null or empty!");
		}
		logger.info("Comet Api Url From GET:{}" , url);

		// Create a URL wrapper class
		HttpUrl.Builder urlBuilder = HttpUrl.Companion.parse(url).newBuilder();

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
				logger.info("Comet Api Parameters From GET:{}" , okHttpRequest.getParameters());
			} catch (Exception e) {
				logger.error("Comet Exception occured.", e.getMessage());
				logger.info("Comet Exception occured.", e);
				throw new AthenaException(e);
			}
		}

		// Add headers if exists
		Headers.Builder headers = new Headers.Builder();
		if (null != okHttpRequest.getHeaders() && !okHttpRequest.getHeaders().isEmpty()) {
			for (String key : okHttpRequest.getHeaders().keySet()) {
				String value = String.valueOf(okHttpRequest.getHeaders().get(key));
				headers.add(key, value);
			}
			logger.info("Comet Api Headers From GET:{}" , okHttpRequest.getHeaders());
		}

		// Define response
		Response response = null;
		try {
			// Build request protocols
			Request request = new Request.Builder().url(urlBuilder.build()).headers(headers.build()).build();
			// Synchronization request-response
			response = OkHttpClientManager.getInstance().getClient().newCall(request).execute();
			if (null == response)
				return null;

			OkHttpResponse okHttpResponse = new OkHttpResponse();
			/*
			 * Results of the request. true:Successful false:fail
			 */
			okHttpResponse.setSuccessful(response.isSuccessful());
			// Response code,please refer to HTTP status code for specific value.
			okHttpResponse.setStatusCode(response.code());
			// Parse the response message.
			okHttpResponse.setResponseData(new String(response.body().bytes(), this.ENCODE));
			
			if (!isWhiteURL(url)) {
				logger.info("Comet Api Response From GET:{}", okHttpResponse.getResponsData());
			}

			return okHttpResponse;

		} catch (Exception e) {
			logger.error("Comet Exception occured.", e.getMessage());
			logger.info("Comet Exception occured.", e);
			throw new AthenaException(e);
		} finally {
			if (null != response) {
				response.close();
			}
		}
	}

	/**
	 * @param okHttpRequest
	 * @return
	 * @throws AthenaException
	 */
	public OkHttpResponse post(OkHttpRequest okHttpRequest) throws AthenaException {

		// Check the required items
		String url = okHttpRequest.getUrl();
		// url non empty check
		if (StringUtil.isNullOrEmpty(url)) {
			logger.error("Url is null or empty!");
			throw new AthenaException("Url is null or empty!");
		}
		logger.info("Comet Api Url From POST:{}" , url);
		// contentType non empty check
		MediaType contentType = okHttpRequest.getContentType();
		if (null == contentType) {
			logger.error("ContentType is empty!");
			throw new AthenaException("ContentType is empty!");
		}

		// Add headers if exists
		Headers.Builder headers = new Headers.Builder();
		if (null != okHttpRequest.getHeaders() && !okHttpRequest.getHeaders().isEmpty()) {
			for (String key : okHttpRequest.getHeaders().keySet()) {
				String value = String.valueOf(okHttpRequest.getHeaders().get(key));
				headers.add(key, value);
			}
			logger.info("Comet Api Headers From POST:{}" , okHttpRequest.getHeaders());
		}

		// Serialize request message
		String jsonBody = "";
		try {
			if (null != okHttpRequest.getParameters()) {
				jsonBody = objectMapper.writeValueAsString(okHttpRequest.getParameters());
			}
			logger.info("Comet Api Parameters From POST:{}" , jc.toString(okHttpRequest.getParameters()));
		} catch (Exception e) {
			logger.error("Comet Exception occured.", e.getMessage());
			logger.info("Comet Exception occured.", e);
			throw new AthenaException(e);
		}

		// Define response
		Response response = null;
		try {
			// Build request protocols
			// Define request
			Request request = new Request.Builder().url(url).headers(headers.build())
					.post(RequestBody.Companion.create(jsonBody, contentType)).build();
			// Synchronization request-response
			response = OkHttpClientManager.getInstance().getClient().newCall(request).execute();
			if (null == response)
				return null;

			OkHttpResponse okHttpResponse = new OkHttpResponse();
			/*
			 * Results of the request. true:Successful false:fail
			 */
			okHttpResponse.setSuccessful(response.isSuccessful());
			// Response code,please refer to HTTP status code for specific value.
			okHttpResponse.setStatusCode(response.code());
			// Parse the response message.
			okHttpResponse.setResponseData(new String(response.body().bytes(), this.ENCODE));

			logger.info("Comet Api Response From POST:{}", okHttpResponse.getResponsData());

			return okHttpResponse;

		} catch (Exception e) {
			logger.error("Comet Exception occured.", e.getMessage());
			logger.info("Comet Exception occured.", e);
			throw new AthenaException(e);
		} finally {
			if (null != response) {
				response.close();
			}
		}
	}

	/**
	 * Delete processing is performed.
	 * 
	 * @param okHttpRequest
	 * @return
	 * @throws AthenaException
	 */
	public OkHttpResponse delete(OkHttpRequest okHttpRequest) throws AthenaException {
		String url = okHttpRequest.getUrl();
		// url non empty check
		if (StringUtil.isNullOrEmpty(url)) {
			logger.error("Url is null or empty!");
			throw new AthenaException("Url is null or empty!");
		}
		logger.info("Comet Api Url From DELETE:{}" , url);
		// contentType non empty check
		MediaType contentType = okHttpRequest.getContentType();
		if (null == contentType) {
			logger.error("ContentType is empty!");
			throw new AthenaException("ContentType is empty!");
		}

		// Add headers if exists
		Headers.Builder headers = new Headers.Builder();
		if (null != okHttpRequest.getHeaders() && !okHttpRequest.getHeaders().isEmpty()) {
			for (String key : okHttpRequest.getHeaders().keySet()) {
				String value = String.valueOf(okHttpRequest.getHeaders().get(key));
				headers.add(key, value);
			}
			logger.info("Comet Api Headers From DELETE:{}" , okHttpRequest.getHeaders());
		}

		// Serialize request message
		String jsonBody = "";
		try {
			// Serialize only if the request parameter exists
			if (null != okHttpRequest.getParameters()) {
				jsonBody = objectMapper.writeValueAsString(okHttpRequest.getParameters());
			}
			logger.info("Comet Api Parameters From DELETE:{}" , okHttpRequest.getParameters());
		} catch (Exception e) {
			logger.error("Comet Exception occured.", e.getMessage());
			logger.info("Comet Exception occured.", e);
			throw new AthenaException(e);
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

			response = OkHttpClientManager.getInstance().getClient().newCall(request).execute();
			if (null == response)
				return null;

			// Encapsulate okhttpresponse according to the result of response
			OkHttpResponse okHttpResponse = new OkHttpResponse();
			// Results of the request. true:Successful false:fail
			okHttpResponse.setSuccessful(response.isSuccessful());
			// Response code,please refer to HTTP status code for specific value.
			okHttpResponse.setStatusCode(response.code());
			// Parse the response message.
			okHttpResponse.setResponseData(new String(response.body().bytes(), this.ENCODE));
			logger.info("Comet Api Response From DELETE:{}", okHttpResponse.getResponsData());

			return okHttpResponse;
		} catch (Exception e) {
			logger.error("Comet Exception occured.", e.getMessage());
			logger.info("Comet Exception occured.", e);
			throw new AthenaException(e);
		} finally {
			if (null != response) {
				response.close();
			}
		}

	}

	/**
	 * Update processing is performed.
	 * 
	 * @param okHttpRequest
	 * @return
	 * @throws AthenaException
	 */
	public OkHttpResponse put(OkHttpRequest okHttpRequest) throws AthenaException {
		String url = okHttpRequest.getUrl();
		// url non empty check
		if (StringUtil.isNullOrEmpty(url)) {
			logger.error("Url is null or empty!");
			throw new AthenaException("Url is null or empty!");
		}
		logger.info("Comet Api Url From PUT:{}" , url);
		// contentType non empty check
		MediaType contentType = okHttpRequest.getContentType();
		if (null == contentType) {
			logger.error("ContentType is empty!");
			throw new AthenaException("ContentType is empty!");
		}

		// Add headers if exists
		Headers.Builder headers = new Headers.Builder();
		if (null != okHttpRequest.getHeaders() && !okHttpRequest.getHeaders().isEmpty()) {
			for (String key : okHttpRequest.getHeaders().keySet()) {
				String value = String.valueOf(okHttpRequest.getHeaders().get(key));
				headers.add(key, value);
			}
			logger.info("Comet Api Headers From PUT:{}" , okHttpRequest.getHeaders());
		}

		// Serialize request message
		String jsonBody = "";
		try {
			// Serialize only if the request parameter exists
			if (null != okHttpRequest.getParameters()) {
				jsonBody = objectMapper.writeValueAsString(okHttpRequest.getParameters());
			}
			logger.info("Comet Api Parameters From PUT:{}" , jc.toString(okHttpRequest.getParameters()));
		} catch (Exception e) {
			logger.error("Comet Exception occured.", e.getMessage());
			logger.info("Comet Exception occured.", e);
			throw new AthenaException(e);
		}

		// Define response
		Response response = null;
		try {
			// Build request protocols
			// put(requestbody) method is used when there are no parameters
			Request request = new Request.Builder().url(url).headers(headers.build())
					.put(RequestBody.Companion.create(jsonBody, contentType)).build();
			response = OkHttpClientManager.getInstance().getClient().newCall(request).execute();
			if (null == response)
				return null;

			// Encapsulate okhttpresponse according to the result of response
			OkHttpResponse okHttpResponse = new OkHttpResponse();
			// Results of the request. true:Successful false:fail
			okHttpResponse.setSuccessful(response.isSuccessful());
			// Response code,please refer to HTTP status code for specific value.
			okHttpResponse.setStatusCode(response.code());
			// Parse the response message.
			okHttpResponse.setResponseData(new String(response.body().bytes(), this.ENCODE));
			logger.info("Comet Api Response From PUT:{}", okHttpResponse.getResponsData());

			return okHttpResponse;
		} catch (Exception e) {
			logger.error("Comet Exception occured.", e.getMessage());
			logger.info("Comet Exception occured.", e);
			throw new AthenaException(e);
		} finally {
			if (null != response) {
				response.close();
			}
		}
	}

}
