package com.sbisec.helios.ap.bizcommon.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 共通関数DTO：FCT028
 * @author base 熊
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class OutputFct028Dto extends BaseOutputDto{

	//売却可能数量(株数)
	private BigDecimal acPositionStockNumber;
	
	//リアルタイム残株数(売却)
	private BigDecimal realtimeBalanceStockNumberSell;
	
	//当日店頭売却株数
	private BigDecimal currentDayOtcSellStockNumber;

}
