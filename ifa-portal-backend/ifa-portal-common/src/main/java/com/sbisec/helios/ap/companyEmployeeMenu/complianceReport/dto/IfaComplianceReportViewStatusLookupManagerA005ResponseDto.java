package com.sbisec.helios.ap.companyEmployeeMenu.complianceReport.dto;

import java.util.List;

import lombok.Data;

@Data
public class IfaComplianceReportViewStatusLookupManagerA005ResponseDto {

	/** コンプライアンス通信一覧リスト. */
	private List<IfaComplianceReportViewStatusLookupManagerA005ResponseDtoComplianceReportList> complianceReportListList;

}
