package com.sbisec.helios.gw.systemManageMenu.portalNotification.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class IfaPortalNotificationManagerLookupA008ApiRequest {

	/** お知らせID（数字）. */
	@NotEmpty(message = "お知らせID")
	@Pattern(regexp="0-9", message = "お知らせID")
	@Size(max = 10, message = "お知らせID")
	private String notificationId;

}
