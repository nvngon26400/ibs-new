package com.sbisec.helios.ap.api.safe.protocol.fundProduct;

import com.sbisec.helios.ap.api.safe.service.fund.product.dto.FundBasicInfoApiOut;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetFundBasicRes {

    private FundBasicInfoApiOut fundBasicInfoApiOut;

}
