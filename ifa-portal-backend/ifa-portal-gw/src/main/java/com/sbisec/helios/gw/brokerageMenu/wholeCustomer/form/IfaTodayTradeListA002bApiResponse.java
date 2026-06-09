package com.sbisec.helios.gw.brokerageMenu.wholeCustomer.form;

import java.util.List;

import lombok.Data;

@Data
public class IfaTodayTradeListA002bApiResponse {

	/** 当日約定リスト. */
	private List<IfaTodayTradeListA002bTodayTradeApiResponse> todayTradeList;

}
