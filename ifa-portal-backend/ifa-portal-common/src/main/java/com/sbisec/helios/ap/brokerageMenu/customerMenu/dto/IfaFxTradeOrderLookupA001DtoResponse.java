package com.sbisec.helios.ap.brokerageMenu.customerMenu.dto;

import java.util.List;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A001Dtoレスポンス
 *
 * @author SCSK
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class IfaFxTradeOrderLookupA001DtoResponse extends ModelBase {
    
    private static final long serialVersionUID = 566150087548978182L;

    /** 取引停止フラグ. */
    private String tradeSuspendFlag;
    
    /** 為替注文情報リスト. */
    private List<IfaFxTradeOrderLookupA001DtoResponseFxOrderInfo> fxOrderInfoList;
    
    /** 通貨リスト. */
    private List<IfaFxTradeOrderLookupA001DtoResponseCurrency> currencyList;
    
    /** 為替取引媒介可否リスト. */
    private List<IfaFxTradeOrderLookupA001DtoResponseFxTradeMediatePropriety> fxTradeMediateProprietyList;
    
}
