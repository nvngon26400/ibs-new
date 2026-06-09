package com.sbisec.helios.gw.companyEmployeeMenu.infoRegister.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class IfaNotificationViewStatusLookupA005ApiRequest {

	/** お知らせリスト.お知らせID */
	@NotEmpty(message = "お知らせリスト.お知らせID")
	private String notificationId;

	/** ログインID */
	@NotEmpty(message = "ログインID")
	@Size(max = 16, message = "ログインID")
	private String loginId;

}
