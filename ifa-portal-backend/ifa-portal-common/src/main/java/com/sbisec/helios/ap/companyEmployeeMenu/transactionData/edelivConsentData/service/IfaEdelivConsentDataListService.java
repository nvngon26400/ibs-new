package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.service;

import com.sbibits.earth.model.DataList;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dto.IfaEdelivConsentDataListA007RequestDto;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dto.IfaEdelivConsentDataListA007ResponseDto;
import com.sbisec.helios.ap.service.Service;

/**
 * 画面ID：SUB0504_02-01
 * 画面名：電子交付同意データ一覧
 */
public interface IfaEdelivConsentDataListService extends Service {

    /**
     * SQL001：顧客属性情報件数取得
     *
     * @param butenCode 部店
     * @param accountNumber 口座番号
     * @return 件数
     * @throws Exception システムエラー
     */
    Integer countCustomerAttributeInfo(String butenCode, String accountNumber) throws Exception;

    /**
     * A007：登録
     *
     * @param dtoReq リクエスト
     * @return レスポンス
     * @throws Exception システムエラー
     */
    DataList<IfaEdelivConsentDataListA007ResponseDto> registerA007(
            IfaEdelivConsentDataListA007RequestDto dtoReq) throws Exception;

}
