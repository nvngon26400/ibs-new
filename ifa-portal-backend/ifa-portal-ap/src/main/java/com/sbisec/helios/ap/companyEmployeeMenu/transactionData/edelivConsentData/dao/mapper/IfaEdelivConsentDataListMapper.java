package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dao.model.IfaEdelivConsentDataListSql002RequestModel;

/**
 * 画面ID：SUB0504_02-01
 * 画面名：電子交付同意データ一覧
 */
@Mapper
public interface IfaEdelivConsentDataListMapper {

    /**
     * SQL001：顧客属性情報件数取得
     *
     * @param butenCode 部店
     * @param accountNumber 口座番号
     * @return 件数
     */
    int countCustomerAttributeInfo(
            @Param("butenCode") String butenCode,
            @Param("accountNumber") String accountNumber);

    /**
     * SQL002：電子交付承諾情報登録更新
     *
     * @param registerList 登録リスト
     */
    void mergeEdelivAgreementInfo(
            @Param("registerList") List<IfaEdelivConsentDataListSql002RequestModel> registerList);

}
