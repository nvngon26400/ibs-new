package com.sbisec.helios.gw.companyEmployeeMenu.transactionData.edelivConsentData.form;

import java.util.List;

import lombok.Data;

/**
 * 画面ID：SUB0504_02-01
 * 画面名：電子交付同意データ一覧
 */
@Data
public class IfaEdelivConsentDataListA007ApiResponse {

    /** 電子交付同意データ一覧 */
    private List<IfaEdelivConsentDataListEdelivConsentDataApiResponse> edelivConsentDataList;

}
