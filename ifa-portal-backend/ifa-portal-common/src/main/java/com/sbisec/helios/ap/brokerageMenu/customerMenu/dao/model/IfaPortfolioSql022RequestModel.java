package com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model;

import lombok.Data;

/**
 * 資産状況 基準価額、基準価額単位取得リクエスト
 *
 * @author SCSK
 */
@Data
public class IfaPortfolioSql022RequestModel {

    /** 銘柄コード（半角英数字）. */
    private String brandCode;
}
