package com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model;

import lombok.Data;

/**
 * 資産状況 基準価額、基準価額単位取得レスポンス
 *
 * @author SCSK
 */
@Data
public class IfaPortfolioSql022ResponseModel {

    /** NRIコード */
    private String nriCd;

    /** 基準価額 */
    private String basePrice;

    /** 基準価額単位 */
    private String basePriceUnit;
}
