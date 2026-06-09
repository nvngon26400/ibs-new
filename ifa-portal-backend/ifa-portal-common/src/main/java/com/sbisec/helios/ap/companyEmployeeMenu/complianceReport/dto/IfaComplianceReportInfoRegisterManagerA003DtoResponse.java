package com.sbisec.helios.ap.companyEmployeeMenu.complianceReport.dto;

import java.util.List;

import lombok.Data;

@Data
public class IfaComplianceReportInfoRegisterManagerA003DtoResponse {

	/** コンプライアンス通信リスト. */
	private List<IfaComplianceReportInfoRegisterManagerA003DtoResponseComplianceReport> complianceReportList;

}
