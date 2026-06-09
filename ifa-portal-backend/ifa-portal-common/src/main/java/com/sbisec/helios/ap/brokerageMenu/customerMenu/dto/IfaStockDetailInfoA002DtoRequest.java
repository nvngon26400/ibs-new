package com.sbisec.helios.ap.brokerageMenu.customerMenu.dto;

import lombok.Data;

@Data
public class IfaStockDetailInfoA002DtoRequest {

	/** 銘柄コード（半角英数字）. */
	private String brandCode;

	/** 市場. */
	private String market;

}
