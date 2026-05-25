package com.sbisec.helios.gw.companyEmployeeMenu.complianceReport.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class IfaComplianceReportViewStatusLookupManagerA003aApiRequest {

	/** 仲介業者名（全角半角）. */
	@NotEmpty(message = "仲介業者名")
	@Size(max = 80, message = "仲介業者名")
	private String brokerName;

	/** 営業員名（全角半角）. */
	@NotEmpty(message = "営業員名")
	@Size(max = 80, message = "営業員名")
	private String brokerChargeName;

	/** タイトル（全角半角）. */
	@NotEmpty(message = "タイトル")
	@Size(max = 255, message = "タイトル")
	private String title;

	/** 閲覧状況（全角半角）. */
	@NotEmpty(message = "閲覧状況")
	@Size(max = 20, message = "閲覧状況")
	private String viewStatus;

	/** 閲覧要否（全角半角）. */
	@NotEmpty(message = "閲覧要否")
	@Size(max = 10, message = "閲覧要否")
	private String viewNecessity;

}
