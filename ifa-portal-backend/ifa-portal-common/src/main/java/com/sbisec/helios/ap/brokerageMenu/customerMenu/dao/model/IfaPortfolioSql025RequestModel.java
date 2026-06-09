package com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model;

import lombok.Data;

/**
 * 資産状況 銘柄属性情報取得リクエスト
 *
 * @author SCSK
 */
@Data
public class IfaPortfolioSql025RequestModel {

    // 銘柄コード 先頭4桁
    private String brandCodeFirst;
    
    // 銘柄コード 末尾1桁
    private String brandCodeLast;

}
