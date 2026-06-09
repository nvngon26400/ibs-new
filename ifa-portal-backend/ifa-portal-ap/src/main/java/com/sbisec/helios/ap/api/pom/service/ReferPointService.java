package com.sbisec.helios.ap.api.pom.service;

import jakarta.validation.constraints.NotNull;

import com.sbisec.helios.ap.api.pom.protocol.ReferPointReq;
import com.sbisec.helios.ap.api.pom.protocol.ReferPointRes;

/**
 * ポイント残高照会
 * 
 * @author SCSK
 *
 */
public interface ReferPointService {
    
    @NotNull
    public ReferPointRes getReferPoint(ReferPointReq req) throws Exception;
}
