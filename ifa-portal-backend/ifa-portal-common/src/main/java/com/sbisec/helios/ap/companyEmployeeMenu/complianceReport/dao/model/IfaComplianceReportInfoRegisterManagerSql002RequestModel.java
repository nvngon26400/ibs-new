package com.sbisec.helios.ap.companyEmployeeMenu.complianceReport.dao.model;

import lombok.Data;

@Data
public class IfaComplianceReportInfoRegisterManagerSql002RequestModel {

	/** 公開フラグ（数字）. */
	private String disclosureFlag;

	/** 更新日時. */
	private String updateTime;

	/** 更新者. */
	private String updater;
	
	/** LECTURE_ID. */
	private String lectureId;
	

}
