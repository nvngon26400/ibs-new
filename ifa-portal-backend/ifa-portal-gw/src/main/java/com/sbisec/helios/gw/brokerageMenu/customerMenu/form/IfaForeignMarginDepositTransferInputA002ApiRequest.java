package com.sbisec.helios.gw.brokerageMenu.customerMenu.form;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * 米株信用保証金振替入力  A002 リクエストパラメータ
 *
 * @author SCSK
 * 
 */
@Data
public class IfaForeignMarginDepositTransferInputA002ApiRequest {
    
    /** 口座選択. */
    @NotEmpty(message = "口座選択")
    private String accountSelect;
    
}
