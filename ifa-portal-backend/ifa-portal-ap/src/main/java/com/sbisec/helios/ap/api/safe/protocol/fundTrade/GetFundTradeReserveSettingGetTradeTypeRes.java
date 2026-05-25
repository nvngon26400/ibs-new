package com.sbisec.helios.ap.api.safe.protocol.fundTrade;

import com.sbisec.helios.ap.api.safe.service.fund.trade.dto.FundReserveCanTradeTypeApiOut;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetFundTradeReserveSettingGetTradeTypeRes {

    private FundReserveCanTradeTypeApiOut fundReserveCanTradeTypeApiOut;

}
