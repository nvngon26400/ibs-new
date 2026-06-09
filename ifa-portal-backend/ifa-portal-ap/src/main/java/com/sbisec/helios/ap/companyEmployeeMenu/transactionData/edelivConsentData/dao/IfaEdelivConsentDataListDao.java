package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dao;

import java.util.List;

import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dao.model.IfaEdelivConsentDataListSql002RequestModel;

/**
 * 画面ID：SUB0504_02-01
 * 画面名：電子交付同意データ一覧
 */
public interface IfaEdelivConsentDataListDao {

    /**
     * SQL001：顧客属性情報件数取得
     *
     * @param butenCode 部店
     * @param accountNumber 口座番号
     * @return 件数
     * @throws Exception システムエラー
     */
    int countCustomerAttributeInfo(String butenCode, String accountNumber) throws Exception;

    /**
     * SQL002：電子交付承諾情報登録更新
     *
     * @param registerList 登録リスト
     * @return 処理結果
     * @throws Exception システムエラー
     */
    int mergeEdelivAgreementInfo(List<IfaEdelivConsentDataListSql002RequestModel> registerList) throws Exception;

}
