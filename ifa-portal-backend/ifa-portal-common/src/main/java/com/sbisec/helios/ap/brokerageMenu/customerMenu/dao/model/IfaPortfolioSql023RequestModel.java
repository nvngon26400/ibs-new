package com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model;

import lombok.Data;

/**
 * 資産状況 外国債券（外貨建）銘柄情報取得リクエスト
 *
 * @author SCSK
 */
@Data
public class IfaPortfolioSql023RequestModel {

    /** 銘柄コード */
    private String brandCode;

}
