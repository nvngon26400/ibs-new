package com.sbisec.helios.gw.companyEmployeeMenu.complianceReport.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class IfaComplianceReportInfoRegisterManagerA003ApiRequest {

	/** 公開フラグ（数字）. */
	@NotEmpty(message = "公開フラグ")
	@Pattern(regexp="0-9", message = "公開フラグ")
	@Size(min = 1, max = 1, message = "公開フラグ")
	private String disclosureFlag;

	/** LECTURE_ID（数字）. */
	@NotEmpty(message = "LECTURE_ID")
	@Pattern(regexp="0-9", message = "LECTURE_ID")
	@Size(max = 38, message = "LECTURE_ID")
	private String lectureId;

}
