package com.sbisec.helios.ap.api.ccsApi.protocol;

import com.sbisec.helios.ap.api.ccsApi.service.dto.ShowDealingDetailIFAIn;
import com.sbisec.helios.ap.api.ccsApi.util.CcsApiUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShowDealingDetailIFAReq implements CcsApiBaseReq {

    // headerとparameterインスタンス化
    private Header header = new Header();

    private ShowDealingDetailIFAIn parameter = new ShowDealingDetailIFAIn();

    @Data
    @NoArgsConstructor
    public class Header {
      
    }

    public String getShowDealingDetailIFAUrl() {
      return CcsApiUtil.getShowDealingDetailIFA();
    }

}
