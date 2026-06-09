package com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model;

import lombok.Data;

/**
 * 資産状況 先OP(保証金等)の資産情報取得リクエスト
 * 
 * @author SCSK
 */
@Data
public class IfaPortfolioSql028RequestModel {

    /** 顧客共通情報.部店コード */
    private String butenCode;

    /** 顧客共通情報.口座番号 */
    private String accountNumber;

}
