package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dto;

import java.util.List;

import lombok.Data;

/**
 * 画面ID：SUB0504_02-01
 * 画面名：電子交付同意データ一覧
 */
@Data
public class IfaEdelivConsentDataListEdelivConsentDataDto {

    /** 部店 */
    private String butenCode;

    /** 口座番号 */
    private String accountNumber;

    /** 電子交付承諾日付 */
    private String edelivAgreementDate;

    /** 電子交付承諾区分 */
    private String edelivAgreementKbn;

    /** チェック結果 */
    private String checkResult;

    /** チェック結果セット */
    private List<IfaEdelivConsentDataListCheckResultSetDto> checkResultSetList;

    /** メッセージを表示する */
    private String errorMessage;

}
