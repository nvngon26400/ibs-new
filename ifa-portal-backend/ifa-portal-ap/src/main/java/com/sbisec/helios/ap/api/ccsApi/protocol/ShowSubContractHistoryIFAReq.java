package com.sbisec.helios.ap.api.ccsApi.protocol;

import com.sbisec.helios.ap.api.ccsApi.service.dto.ShowSubContractHistoryIFAIn;
import com.sbisec.helios.ap.api.ccsApi.util.CcsApiUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShowSubContractHistoryIFAReq implements CcsApiBaseReq {

    // headerとparameterインスタンス化
    private Header header = new Header();

    private ShowSubContractHistoryIFAIn parameter = new ShowSubContractHistoryIFAIn();

    @Data
    @NoArgsConstructor
    public class Header {
      
    }

    public String getShowSubContractHistoryIFAUrl() {
      return CcsApiUtil.getShowSubContractHistoryIFA();
    }

}
