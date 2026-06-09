package com.sbisec.helios.gw.brokerageMenu.customerMenu.form;

import lombok.Data;

@Data
public class IfaMarginNewOrderInputA005ApiRequest {
    
    /** 取引種別. */
    private String tradeCd;
    
    /** 市場. */
    private String market;
    
    /** 銘柄コード（半角英数字）. */
    private String brandCode;
    
}
