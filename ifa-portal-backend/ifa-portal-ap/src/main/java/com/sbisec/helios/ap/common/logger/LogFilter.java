package com.sbisec.helios.ap.common.logger;

import java.io.IOException;

import org.slf4j.MDC;

import com.ibm.icu.text.MessageFormat;
import com.sbibits.earth.mdc.enums.MdcLogParameter;
import com.sbisec.helios.ap.common.constants.AppConstants;
import com.sbisec.helios.ap.common.enums.HttpHeaderEnum;
import com.sbisec.helios.ap.common.enums.LogKeyEnum;
import com.sbisec.helios.ap.common.enums.LogTypeEnum;
import com.sbisec.helios.ap.common.filter.wrapper.BodyRequestWrapper;
import com.sbisec.helios.ap.common.util.HttpRequestUtil;
import com.sbisec.helios.ap.common.util.ServletUtil;
import com.sbisec.helios.ap.common.util.StringUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Log file content settings.
 * <p>
 * It includes the type of request, the IP currently accessing the service, the user Id or batch Id.
 * </p>
 * 
 * @Organization SBIBITS DaLian CB Group
 */
public class LogFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = ((HttpServletResponse) response);

            // put ip address
            MDC.put(LogKeyEnum.ip.getKey(), HttpRequestUtil.getRemoteAddress(req));

            String tid = "TID" + req.getHeader("tid");
            String fid = req.getHeader("fid");
            String body = ServletUtil.instance(req).readRequest();
            String serviceName = StringUtil.jsonValueByKey(body, "serviceName");
            String methodName = StringUtil.jsonValueByKey(body, "methodName");
    
            // Set LogParameter.
            MDC.put(LogKeyEnum.kind.getKey(), LogTypeEnum.WEB.getType());
            MDC.put(MdcLogParameter.MDC_TID.getId(), MessageFormat.format(AppConstants.MDC_FORMAT_TID, Thread.currentThread().threadId()));
            MDC.put(MdcLogParameter.MDC_FID.getId(), MessageFormat.format(AppConstants.MDC_FORMAT_FID, req.getRequestURI(), req.getMethod()));
            MDC.put(MdcLogParameter.MDC_FSID.getId(),   serviceName + "." + methodName);
            MDC.put(MdcLogParameter.MDC_SYS_ID.getId(), "helios-gw");
            MDC.put(MdcLogParameter.MDC_INH_ID.getId(), MessageFormat.format(AppConstants.MDC_FORMAT_INH_ID, fid, tid));
            
            // header
            String frameworkSessionId = req.getHeader(HttpHeaderEnum.SESSION_ID.getName());
            MDC.put(LogKeyEnum.session_id.getKey(), frameworkSessionId != null ? frameworkSessionId : "");
            String userId = req.getHeader("userId");
            MDC.put("user_id", userId != null ? userId : "");
            
            chain.doFilter(new BodyRequestWrapper(req, body), resp);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
