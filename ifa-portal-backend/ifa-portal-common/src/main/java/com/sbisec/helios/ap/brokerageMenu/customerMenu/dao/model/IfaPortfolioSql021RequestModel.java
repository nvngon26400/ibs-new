package com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model;

import lombok.Data;

/**
 * 資産状況 優先市場取得リクエスト
 *
 * @author SCSK
 */
@Data
public class IfaPortfolioSql021RequestModel {
    /** 銘柄コード */
    private String ipmProductCode;
}
