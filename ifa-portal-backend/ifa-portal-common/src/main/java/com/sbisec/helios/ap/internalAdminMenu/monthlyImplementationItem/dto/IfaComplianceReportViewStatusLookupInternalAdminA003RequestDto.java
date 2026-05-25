package com.sbisec.helios.ap.internalAdminMenu.monthlyImplementationItem.dto;

import lombok.Data;

@Data
public class IfaComplianceReportViewStatusLookupInternalAdminA003RequestDto {

    /** 仲介業者名（全角半角）. */
    private String brokerName;

    /** 営業員名（全角半角）. */
    private String brokerChargeName;

    /** タイトル（全角半角）. */
    private String title;

    /** 閲覧状況（全角半角）. */
    private String viewStatus;

    /** 閲覧対象（全角半角）. */
    private String viewTarget;

}
