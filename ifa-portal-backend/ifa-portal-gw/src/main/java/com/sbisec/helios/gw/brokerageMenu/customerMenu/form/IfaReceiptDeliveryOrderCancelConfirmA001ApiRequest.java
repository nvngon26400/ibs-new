package com.sbisec.helios.gw.brokerageMenu.customerMenu.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class IfaReceiptDeliveryOrderCancelConfirmA001ApiRequest {

	/** EC受注番号（半角英数字）. */
	@NotEmpty(message = "EC受注番号")
	@Size(min = 6, max = 6, message = "EC受注番号")
	private String ecOrderNo;

}
