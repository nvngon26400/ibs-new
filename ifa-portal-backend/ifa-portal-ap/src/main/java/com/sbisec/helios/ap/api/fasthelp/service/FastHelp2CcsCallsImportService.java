package com.sbisec.helios.ap.api.fasthelp.service;


import com.sbisec.helios.ap.api.fasthelp.protocol.fasthelp.GetFasthelpCcsCallsImportReq;
import com.sbisec.helios.ap.api.fasthelp.protocol.fasthelp.GetFasthelpCcsCallsImportRes;

public interface FastHelp2CcsCallsImportService {

    /**
     * FastHelp問合せデータ登録
     * POST:/fasthelp-server/ccsCallsImport
     * 
     */
    public GetFasthelpCcsCallsImportRes getFasthelpCcsCallsImport(GetFasthelpCcsCallsImportReq req)
        throws Exception;
}
