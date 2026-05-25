package com.sbisec.helios.ap.brokerageMenu.customerMenu.dto;

import lombok.Data;

@Data
public class IfaCustomerPortalA001RequestDto {

	/** 部店コード（半角英数字）. */
	private String butenCode;

	/** 口座番号（数字）. */
	private String accountNumber;

}
