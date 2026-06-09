package com.sbisec.helios.ap.internalAdminMenu.monthlyImplementationItem.dto;

import java.util.List;

import lombok.Data;

@Data
public class IfaComplianceReportViewStatusLookupInternalAdminA002ResponseDto {

	/** コンプライアンス通信リスト. */
	private List<IfaComplianceReportViewStatusLookupInternalAdminA002ResponseDtoComplianceReport> complianceReportList;

}
