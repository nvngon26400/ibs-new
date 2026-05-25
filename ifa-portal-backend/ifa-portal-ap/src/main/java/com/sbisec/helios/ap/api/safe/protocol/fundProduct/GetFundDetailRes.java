package com.sbisec.helios.ap.api.safe.protocol.fundProduct;

import com.sbisec.helios.ap.api.safe.service.fund.product.dto.FundDetailInfoApiOut;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetFundDetailRes {

    private FundDetailInfoApiOut fundDetailInfoApiOut;

}
