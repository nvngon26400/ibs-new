package com.sbisec.helios.ap.brokerageMenu.wholeCustomer.dto;

import lombok.Data;

@Data
public class IfaMarginPositionListDomesticErrorModel {
    
    /** エラーメッセージ */
    private String errorMessage;
    
    /** エラーコード */
    private String errorCode;
    
}
