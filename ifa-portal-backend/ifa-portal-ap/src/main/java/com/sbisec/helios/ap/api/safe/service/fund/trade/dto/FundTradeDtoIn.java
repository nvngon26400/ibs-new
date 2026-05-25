package com.sbisec.helios.ap.api.safe.service.fund.trade.dto;

import com.sbisec.helios.ap.api.safe.service.common.dto.DtoIn;

/**
 * FundTradeServiceのInputのDtoベース
 */
public abstract class FundTradeDtoIn extends DtoIn {

    /**
     * コンストラクタ
     * @param id APIアクセス時のid
     */
    public FundTradeDtoIn(String id) {
        super("fundTrade", "1.0.0", id);
    }
}
