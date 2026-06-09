package com.sbisec.helios.ap.api.athena.protocol.exchange.order;

import java.util.List;

import com.sbisec.helios.ap.api.athena.protocol.common.Page;
import com.sbisec.helios.ap.api.athena.protocol.exchange.order.dto.ExchangeOrderDetail;

import lombok.Data;

@Data
public class ListExchangeOrdersRes {
    
    public ListExchangeOrdersRes() {
    
    }
    
    /** 為替注文情報 */
    private List<ExchangeOrderDetail> orderDetails;
    
    /** ページング */
    private Page page;
}
