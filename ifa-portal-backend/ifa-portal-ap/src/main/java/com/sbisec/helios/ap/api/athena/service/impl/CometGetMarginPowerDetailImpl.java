package com.sbisec.helios.ap.api.athena.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sbibits.earth.util.StringUtil;
import com.sbisec.helios.ap.api.athena.OkHttpResponse;
import com.sbisec.helios.ap.api.athena.enums.CountryCode;
import com.sbisec.helios.ap.api.athena.protocol.account.GetMarginPowerDetailReq;
import com.sbisec.helios.ap.api.athena.protocol.account.GetMarginPowerDetailResp;
import com.sbisec.helios.ap.api.athena.service.AbstractBaseService;
import com.sbisec.helios.ap.api.athena.service.CometGetMarginPowerDetail;
import com.sbisec.helios.ap.api.athena.utils.AthenaException;
import com.sbisec.helios.ap.api.athena.utils.CometApiUtil;

/**
 * 余力サービス Service implements.

 * @author SCSK 矢口
    2023/12/1 新規作成
 */
@Service
public class CometGetMarginPowerDetailImpl extends AbstractBaseService implements CometGetMarginPowerDetail {
    
    private static final Logger LOG = LoggerFactory.getLogger(CometGetMarginPowerDetailImpl.class);
    
    @Override
    public GetMarginPowerDetailResp getMarginPowerDetail(GetMarginPowerDetailReq request) throws Exception {
        
        long start = System.currentTimeMillis();
        
        if (LOG.isDebugEnabled()) {
            LOG.debug("CometGetMarginPowerDetailImpl.getMarginPowerDetail : {}", hashCode());
        }
        
        // パラメータチェック
        String warnMsg = StringUtil.EMPTY_STRING;
        do {
            // request not empty check.
            if (null == request) {
                warnMsg = MSG_REQUEST_IS_NULL;
                break;
            }
            
            // Token not empty check
            String token = request.getHeader().getToken();
            if (StringUtil.isNullOrEmpty(token)) {
                warnMsg = "Token is null or empty!";
                break;
            }
            if (!checkToken(token)) {
                warnMsg = MSG_TOKEN_FORMAT;
                break;
            }
            
            // 必須入力チェック「countryCode」
            String countryCode = request.getParameter().getCountryCode();
            if (StringUtil.isNullOrEmpty(countryCode) || null == CountryCode.getById(countryCode)) {
                warnMsg = "CountryCode is not exists or empty!";
                break;
            }
            
        } while (false);
        if (!StringUtil.isNullOrEmpty(warnMsg)) {
            LOG.error("Comet Exception ： " + warnMsg);
            throw new AthenaException("Parameter verification failed!");
        }
        
        String url = this.getUrl(CometApiUtil.getAcc_balance_margin_powers_getDetail());
        
        // get要求を送信
        OkHttpResponse httpResp = this.get(url, request);
        
        if (LOG.isDebugEnabled()) {
            LOG.debug("Comet response data => {}", httpResp.getResponsData());
        }
        // 設定応答メッセージ
        GetMarginPowerDetailResp resp = null;
        try {
            // convert the string into entity bean and return it.
            resp = httpResp.getResponseData(GetMarginPowerDetailResp.class);
        } catch (Exception e) {
            LOG.warn("Comet response data deserialization exception:", e);
            throw e;
        }
        
        if (LOG.isDebugEnabled()) {
            LOG.debug("cost -> {}", (System.currentTimeMillis() - start));
        }
        
        // 結果を返します
        return resp;
    }
}
