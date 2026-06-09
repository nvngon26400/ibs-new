package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dao.model;

import lombok.Data;

/**
 * 画面ID：SUB0504_02-01
 * 画面名：電子交付同意データ一覧
 * SQL002：電子交付承諾情報登録更新
 */
@Data
public class IfaEdelivConsentDataListSql002RequestModel {

    /** 部店 */
    private String butenCode;

    /** 口座番号 */
    private String accountNumber;

    /** 電子交付承諾日付 */
    private String edelivAgreementDate;

    /** 電子交付承諾区分 */
    private String edelivAgreementKbn;

    /** ユーザID */
    private String userId;

}
