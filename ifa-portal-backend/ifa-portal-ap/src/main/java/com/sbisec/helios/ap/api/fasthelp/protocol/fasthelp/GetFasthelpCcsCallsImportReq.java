package com.sbisec.helios.ap.api.fasthelp.protocol.fasthelp;

import com.sbisec.helios.ap.api.fasthelp.protocol.FastHelpBaseRequest;
import com.sbisec.helios.ap.api.fasthelp.service.dto.fasthelp.FasthelpCcsCallsImportIn;
import com.sbisec.helios.ap.api.fasthelp.utils.FastHelpApiUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetFasthelpCcsCallsImportReq implements FastHelpBaseRequest {

    // headerとparameterインスタンス化
    private Header header = new Header();

    private FasthelpCcsCallsImportIn parameter = new FasthelpCcsCallsImportIn();

    @Data
    @NoArgsConstructor
    public class Header {
    }

    public String getFasthelpApiUrl() {
        return FastHelpApiUtil.getFasthelp_ccs_calls_import();
    }

}
