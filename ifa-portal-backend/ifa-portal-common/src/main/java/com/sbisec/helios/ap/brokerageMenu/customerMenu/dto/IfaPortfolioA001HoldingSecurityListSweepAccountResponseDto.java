package com.sbisec.helios.ap.brokerageMenu.customerMenu.dto;

import lombok.Data;

/**
 * 画面ID：SUB0202_0101-01
 * 画面名：資産状況
 * A001 レスポンスDTO.保有商品一覧_スイープ専用銀行口座
 *
 * @author SCSK 
 *
 */
@Data
public class IfaPortfolioA001HoldingSecurityListSweepAccountResponseDto {

    /** 名称 */
    private String name;

    /** 評価額合計（数値(整数)） */
    private String valuationTotal;

    /** 為替レート（数値(小数)） */
    private String fxRate;

    /** 評価額（外貨）（数値(小数)） */
    private String foreignValuation;

    /** 評価損益 */
    private String profitAndLoss;

}
