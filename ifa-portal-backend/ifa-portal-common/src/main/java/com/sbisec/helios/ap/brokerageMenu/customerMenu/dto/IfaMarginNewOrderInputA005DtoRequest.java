package com.sbisec.helios.ap.brokerageMenu.customerMenu.dto;

import lombok.Data;

@Data
public class IfaMarginNewOrderInputA005DtoRequest {
    
    /** 取引種別. */
    private String tradeCd;
    
    /** 市場. */
    private String market;
    
    /** 銘柄コード（半角英数字）. */
    private String brandCode;
    
}
