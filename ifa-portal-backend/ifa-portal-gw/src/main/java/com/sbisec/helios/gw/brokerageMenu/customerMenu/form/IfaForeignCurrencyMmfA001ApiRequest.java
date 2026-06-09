package com.sbisec.helios.gw.brokerageMenu.customerMenu.form;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * 外貨建MMF
 * 2025/01/28 新規作成
 *
 * @author 大連 葉
 */
@Data
@JsonSerialize
public class IfaForeignCurrencyMmfA001ApiRequest {

    private String urlId;

    private String patternId;

    private String httpMethod;
}
