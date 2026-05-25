package com.sbisec.helios.ap.api.fasthelp.service;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbibits.earth.util.StringUtil;
import com.sbisec.helios.ap.api.fasthelp.FastHelpOkHttpClientWrapper;
import com.sbisec.helios.ap.api.fasthelp.FastHelpOkHttpRequest;
import com.sbisec.helios.ap.api.fasthelp.FastHelpOkHttpResponse;
import com.sbisec.helios.ap.api.fasthelp.exception.FastHelpException;
import com.sbisec.helios.ap.api.fasthelp.protocol.FastHelpBaseRequest;
import com.sbisec.helios.ap.api.fasthelp.utils.FastHelpConfig;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * AbstractBaseService.
 * 
 * @author dalian
 * @date 04/29/2025
 */
public abstract class AbstractBaseService {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractBaseService.class);

    @Autowired
    private FastHelpConfig fasthelpConfig;

    @Autowired
    private FastHelpOkHttpClientWrapper fastHelpOkHttpClientWrapper;
    
    protected final List<String> getCcsUrl(String api) {
        String[] url = fasthelpConfig.getHostIpCcs().split(",");
        List<String> urlList = Arrays.stream(url)
                .map(ip -> fasthelpConfig.getHostProtocol() + "://" + ip + ":" + fasthelpConfig.getHostPort() + api)
                .collect(Collectors.toList());
        Collections.shuffle(urlList);
        return urlList;
    }

    protected final List<String> getFasthelpUrl(String api) {
        String[] url = fasthelpConfig.getHostIpFasthelp().split(",");
        List<String> urlList = Arrays.stream(url)
                .map(ip -> fasthelpConfig.getHostProtocol() + "://" + ip + ":" + fasthelpConfig.getHostPort() + api)
                .collect(Collectors.toList());
        Collections.shuffle(urlList);
        return urlList;
    }

    /**
     * header & parameterの正確性を確認する.
     * 
     * @param token
     * @param parameter
     * @return チェック結果(void)
     * @throws FastHelpException
     */
    protected final void checkParameter(Object parameter) throws FastHelpException {
        // パラメータチェック
        String warnMsg = StringUtil.EMPTY_STRING;
        do {
            if (parameter == null) {
                warnMsg = "apiIn is not exists or empty!";
                break;
            }
        } while (false);
        if (!StringUtil.isNullOrEmpty(warnMsg)) {
            LOG.error("Fasthelp Exception ： " + warnMsg);
            throw new FastHelpException("Parameter verification failed!");
        }
    }

    protected final FastHelpOkHttpResponse post(List<String> urls, FastHelpBaseRequest req) throws Exception {
        FastHelpOkHttpResponse response = null;
        for (String url : urls) {
            try {
                LOG.info("Fasthelp api Round-robin Url Start:" + url);
                // Create Fasthelp's request protocol class
                FastHelpOkHttpRequest request = new FastHelpOkHttpRequest();
                // Set URL
                request.setUrl(url);
                // Set request body
                RequestBody requestBody = toRequestBody(req.getParameter());
                // Execute post request with RequestBody
                response = fastHelpOkHttpClientWrapper.post(request, requestBody);
                LOG.info("Fasthelp api Round-robin Url End!");
                break;
            } catch (Exception e) {
                LOG.warn("Fasthelp api Round-robin Url Next!");
                continue;
            }
        }
        // Check Response
        checkFasthelpResponseException(response);
        return response;
    }

    protected void checkFasthelpResponseException(FastHelpOkHttpResponse res) throws Exception {
        if (null == res) {
            LOG.warn("Fasthelp api Response is null!");
            throw new FastHelpException("Response is null!");
        }
        if (!res.getSuccessful()) {
            if (res.getStatusCode() >= 500 && res.getStatusCode() < 600) {
                LOG.error("Fasthelp Exception request failed:{}", res.getResponsData());
            } else {
                LOG.info("Fasthelp api request failed:{}", res.getResponsData());
            }
            //システム異常を投げる
            throw new FastHelpException("Fasthelp api request failed!");
        }
    }

    private RequestBody toRequestBody(Object parameters) throws FastHelpException {
        try {
            StringBuilder formDataBuilder = new StringBuilder();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> parameterMap = objectMapper.readValue(
                    objectMapper.writeValueAsString(parameters), 
                    new TypeReference<Map<String, Object>>() {});
            if (parameterMap != null) {
                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                    if (entry.getValue() != null) {
                        String encodedKey = URLEncoder.encode(entry.getKey(), "Shift_JIS");
                        String encodedValue = URLEncoder.encode(entry.getValue().toString(), "Shift_JIS");
                        formDataBuilder.append(encodedKey)
                                        .append("=")
                                        .append(encodedValue)
                                        .append("&");
                    }
                }
                if (formDataBuilder.length() > 0) {
                    formDataBuilder.deleteCharAt(formDataBuilder.length() - 1);
                }
            }
            return RequestBody.create(
                    formDataBuilder.toString().getBytes(Charset.forName("Shift_JIS")),
                    MediaType.parse("application/x-www-form-urlencoded;charset=Shift_JIS")
            );
        } catch (Exception e) {
            throw new FastHelpException(e);
        }
    }
}