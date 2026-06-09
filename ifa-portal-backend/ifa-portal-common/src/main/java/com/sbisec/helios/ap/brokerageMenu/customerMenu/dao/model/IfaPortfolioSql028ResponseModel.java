package com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model;

import lombok.Data;

/**
 * 資産状況 先OP(保証金等)の資産情報取得レスポンス
 *
 * @author SCSK
 */
@Data
public class IfaPortfolioSql028ResponseModel {

    /** 証券種別コード */
    private String securityClassCode;

    /** 証券種別 */
    private String securityClass;

    /** 評価額　*/
    private String valuation;
}
