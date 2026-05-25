package com.sbisec.helios.ap.api.safe.protocol.fundTrade;

import com.sbisec.helios.ap.api.safe.protocol.SafeBaseRequest;
import com.sbisec.helios.ap.api.safe.service.fund.trade.dto.FundReserveSettingChangeReceptApiIn;
import com.sbisec.helios.ap.api.safe.utils.SafeApiUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostFundReserveSettingChangeReceptReq implements SafeBaseRequest {

    // headerとparameterインスタンス化
    private Header header = new Header();

    private FundReserveSettingChangeReceptApiIn parameter = new FundReserveSettingChangeReceptApiIn();

    @Data
    @NoArgsConstructor
    public class Header {

        private String token;

    }

    public String getSafeApiUrl() {
        return SafeApiUtil.postSafe_fundTrade_fund_reserve_setting_change_recept();
    }

}
