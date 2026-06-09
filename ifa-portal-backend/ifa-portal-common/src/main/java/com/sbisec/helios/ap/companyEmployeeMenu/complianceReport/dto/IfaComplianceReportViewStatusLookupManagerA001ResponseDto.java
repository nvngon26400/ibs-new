package com.sbisec.helios.ap.companyEmployeeMenu.complianceReport.dto;

import java.util.List;

import lombok.Data;

@Data
public class IfaComplianceReportViewStatusLookupManagerA001ResponseDto {

	/** コンプライアンス通信リスト. */
	private List<IfaComplianceReportViewStatusLookupManagerA001ResponseDtoComplianceReport> complianceReportList;

}
