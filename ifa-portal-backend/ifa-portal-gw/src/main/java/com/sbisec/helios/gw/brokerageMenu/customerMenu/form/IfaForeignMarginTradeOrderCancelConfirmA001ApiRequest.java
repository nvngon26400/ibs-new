package com.sbisec.helios.gw.brokerageMenu.customerMenu.form;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Data
public class IfaForeignMarginTradeOrderCancelConfirmA001ApiRequest {

	/** 注文Sub番号（数字）. */
	@Digits(integer = 18, fraction = 0, message = "注文Sub番号")
	@NotEmpty(message = "注文Sub番号")
	@Pattern(regexp="0-9", message = "注文Sub番号")
	private String orderSubNumber;

}
