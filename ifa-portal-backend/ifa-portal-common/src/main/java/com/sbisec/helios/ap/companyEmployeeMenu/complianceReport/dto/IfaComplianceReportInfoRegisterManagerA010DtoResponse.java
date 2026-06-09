package com.sbisec.helios.ap.companyEmployeeMenu.complianceReport.dto;

import java.util.List;

import lombok.Data;

@Data
public class IfaComplianceReportInfoRegisterManagerA010DtoResponse {

	/** コンプライアンス通信リスト. */
	private List<IfaComplianceReportInfoRegisterManagerA010DtoResponseComplianceReport> complianceReportList;

}
