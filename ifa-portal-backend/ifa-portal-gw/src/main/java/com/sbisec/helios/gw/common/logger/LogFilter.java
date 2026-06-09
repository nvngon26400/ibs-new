package com.sbisec.helios.gw.common.logger;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.ibm.icu.text.MessageFormat;
import com.sbibits.earth.mdc.enums.MdcLogParameter;
import com.sbisec.helios.ap.common.constants.AppConstants;
import com.sbisec.helios.ap.common.enums.HttpHeaderEnum;
import com.sbisec.helios.ap.common.enums.LogKeyEnum;
import com.sbisec.helios.ap.common.enums.LogTypeEnum;
import com.sbisec.helios.ap.common.model.UserAccount;
import com.sbisec.helios.ap.common.util.HttpRequestUtil;
import com.sbisec.helios.ap.common.util.IfaCommonUtil;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);
    
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

            String tid = MessageFormat.format(AppConstants.MDC_FORMAT_TID, Thread.currentThread().threadId());
            String fid = MessageFormat.format(AppConstants.MDC_FORMAT_FID, req.getRequestURI(), req.getMethod());
            
            // Set LogParameter.
            MDC.put(LogKeyEnum.kind.getKey(), LogTypeEnum.WEB.getType());
            MDC.put(MdcLogParameter.MDC_TID.getId(), tid);
            MDC.put(MdcLogParameter.MDC_FID.getId(), fid);
            MDC.put(MdcLogParameter.MDC_INH_ID.getId(), MessageFormat.format(AppConstants.MDC_FORMAT_INH_ID, fid, tid));
            
            // Headerになければ空文字とする
            String frameworkSessionId = req.getHeader(HttpHeaderEnum.SESSION_ID.getName());
            MDC.put(LogKeyEnum.session_id.getKey(), frameworkSessionId != null ? frameworkSessionId : "");
            // ユーザIDを取得する。取得できない場合は空文字とする
            UserAccount ua = IfaCommonUtil.getUserAccount();
            String userId = "";
            if (ua != null) {
                userId = ua.getUserId();
            }
            MDC.put("user_id", userId);
            
            // ログレベルがDEBUGに設定されている場合にのみ実行
            if (LOGGER.isDebugEnabled()) {
                // フィルタチェーンを続行する前に、リクエストをラップしてキャッシュを有効化
                // これにより、後続のフィルターやサーブレットでリクエストボディを読み出しても、ログ出力のために再度読み出すことができるようになる
                ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(req);
                chain.doFilter(wrappedRequest, resp);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
