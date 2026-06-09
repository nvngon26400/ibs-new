package com.sbisec.helios.gw.brokerageMenu.wholeCustomer.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * 仕組債銘柄情報　A001　リクエスト

 * @author SCSK川崎
 */
@Data
public class IfaStructuredBondBrandInfoA001ApiRequest {
    
    /** 銘柄コード（半角英数字）. */
    @NotEmpty(message = "銘柄コード")
    @Size(max = 5, message = "銘柄コード")
    private String brandCode;
    
}
