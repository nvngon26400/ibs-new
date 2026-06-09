package com.sbisec.helios.ap.api.fasthelp.protocol.ccs;

import com.sbisec.helios.ap.api.fasthelp.protocol.FastHelpBaseRequest;
import com.sbisec.helios.ap.api.fasthelp.service.dto.ccs.CcsFastHelpInfoInsertDoIn;
import com.sbisec.helios.ap.api.fasthelp.utils.FastHelpApiUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetCcsFastHelpInfoInsertDoReq implements FastHelpBaseRequest{

    // headerとparameterインスタンス化
    private Header header = new Header();

    private CcsFastHelpInfoInsertDoIn parameter = new CcsFastHelpInfoInsertDoIn();

    @Data
    @NoArgsConstructor
    public class Header {
      
    }

    public String getFasthelpApiUrl() {
      return FastHelpApiUtil.getCcs_fastHelp_info_insert_do();
    }

}
