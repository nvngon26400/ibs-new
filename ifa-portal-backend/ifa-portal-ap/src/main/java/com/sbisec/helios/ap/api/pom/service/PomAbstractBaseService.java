package com.sbisec.helios.ap.api.pom.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import com.sbisec.helios.ap.api.pom.PomOkHttpClientWrapper;
import com.sbisec.helios.ap.api.pom.PomOkHttpRequest;
import com.sbisec.helios.ap.api.pom.PomOkHttpResponse;
import com.sbisec.helios.ap.api.pom.utils.PomConfig;
import com.sbisec.helios.ap.api.pom.utils.PomException;

/**
 * AbstractBaseService.
 * 
 * 
 */
public abstract class PomAbstractBaseService {

    private static final Logger LOG = LoggerFactory.getLogger(PomAbstractBaseService.class);

    @Autowired
    private PomConfig pomConfig;

    @Autowired
    private PomOkHttpClientWrapper okHttpClientWrapper;

    /**
     * Get url address
     * 
     * @param api API 
     * @return URL
     */
    protected final String getUrl(String api) {
        return pomConfig.getHostProtocol() + "://" + pomConfig.getHostIp() + ":" + pomConfig.getHostPort() + api;
    }

    /**
     * 
     * @param url API
     * @param req 検索条件
     * @return OkHttpResponse
     * @throws Exception 例外発生時
     */
    protected final PomOkHttpResponse get(String url, Object req) throws Exception {
        
        // Create Pom's request protocol class
        PomOkHttpRequest request = new PomOkHttpRequest();
        // Set URL
        request.setUrl(url);
        // Set request parameters
        request.setParameters(req);
        var sw = new StopWatch();
        sw.start();
        if (LOG.isDebugEnabled()) {
            LOG.debug("request url : {}", url);
        }
        // Execute get request
        PomOkHttpResponse res = okHttpClientWrapper.get(request);
        sw.stop();
        if (LOG.isDebugEnabled()) {
            LOG.debug("cost -> {},{}", url, sw.prettyPrint());
        }
        // Check Response
        checkResponseException(res);
        
        // Return response.
        return res;
    }

    /**
     * 
     * @param url API
     * @param req 検索条件
     * @return OkHttpResponse
     * @throws Exception 例外発生時
     */
    protected final PomOkHttpResponse post(String url, Object req) throws Exception {
        
        // Create Pom's request protocol class
        PomOkHttpRequest request = new PomOkHttpRequest();
        // Set URL
        request.setUrl(url);
        // Set request parameters
        request.setParameters(req);
        var sw = new StopWatch();
        sw.start();
        if (LOG.isDebugEnabled()) {
            LOG.debug("request ->{}", url);
        }
        // Execute get request
        PomOkHttpResponse res = okHttpClientWrapper.post(request);
        sw.stop();
        if (LOG.isDebugEnabled()) {
            LOG.debug("cost -> {},{}", url, sw.prettyPrint());
        }
        
        // Check Response
        checkResponseException(res);
        
        // Return response.
        return res;
    }

    /**
     * 
     * @param url API
     * @param req 検索条件
     * @return OkHttpResponse
     * @throws Exception 例外発生時
     */
    protected final PomOkHttpResponse put(String url, Object req) throws Exception {
        
        // Create Pom's request protocol class
        PomOkHttpRequest request = new PomOkHttpRequest();
        // Set URL
        request.setUrl(url);
        // Set request parameters
        request.setParameters(req);
        var sw = new StopWatch();
        sw.start();
        if (LOG.isDebugEnabled()) {
            LOG.debug("request ->{}", url);
        }
        // Execute get request
        PomOkHttpResponse res = okHttpClientWrapper.put(request);
        sw.stop();
        if (LOG.isDebugEnabled()) {
            LOG.debug("cost -> {},{}", url, sw.prettyPrint());
        }
        
        // Check Response
        checkResponseException(res);
        
        // Return response.
        return res;
    }

    /**
     * 
     * @param url API
     * @param req 検索条件
     * @return OkHttpResponse
     * @throws Exception 例外発生時
     */
    protected final PomOkHttpResponse delete(String url, Object req) throws Exception {

        // Create Pom's request protocol class
        PomOkHttpRequest request = new PomOkHttpRequest();
        // Set URL
        request.setUrl(url);
        // Set request parameters
        request.setParameters(req);
        var sw = new StopWatch();
        sw.start();
        if (LOG.isDebugEnabled()) {
            LOG.debug("request ->{}", url);
        }
        // Execute get request
        PomOkHttpResponse res = okHttpClientWrapper.delete(request);
        sw.stop();
        if (LOG.isDebugEnabled()) {
            LOG.debug("cost -> {},{}", url, sw.prettyPrint());
        }
        // Check Response
        checkResponseException(res);
        
        // Return response.
        return res;
    }
    
    private void checkResponseException(PomOkHttpResponse res) throws Exception {
        
        if (Objects.isNull(res)) {
            LOG.warn("Pom api Response is null!");
            throw new PomException("Response is null!");
        }
        if (!res.getSuccessful()) {
            LOG.error("Pom Exception request failed:{}", res.getResponsData());
            throw new PomException(res.getResponsData());
        }
    }
}
