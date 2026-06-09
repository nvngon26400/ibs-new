package com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model;

import lombok.Data;

/**
 * 資産状況 債券ST銘柄コード取得リクエスト
 * 
 * @author SCSK
 */
@Data
public class IfaPortfolioSql027RequestModel {
    
    /** 銘柄コード. */
    private String brandCode;

}
