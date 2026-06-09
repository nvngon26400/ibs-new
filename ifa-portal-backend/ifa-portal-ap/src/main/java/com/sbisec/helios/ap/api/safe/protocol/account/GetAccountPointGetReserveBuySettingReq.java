package com.sbisec.helios.ap.api.safe.protocol.account;

import com.sbisec.helios.ap.api.safe.protocol.SafeBaseRequest;
import com.sbisec.helios.ap.api.safe.service.account.dto.ReserveBuySettingGetApiIn;
import com.sbisec.helios.ap.api.safe.utils.SafeApiUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAccountPointGetReserveBuySettingReq implements SafeBaseRequest {

    // headerとparameterインスタンス化
    private Header header = new Header();

    private ReserveBuySettingGetApiIn parameter = new ReserveBuySettingGetApiIn();

    @Data
    @NoArgsConstructor
    public class Header {

        private String token;

    }

    public String getSafeApiUrl() {
        return SafeApiUtil.getSafe_account_account_point_get_reserve_buy_setting();
    }

}
