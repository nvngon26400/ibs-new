package com.sbisec.helios.gw.brokerageMenu.customerMenu.form;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * 注文状況一覧
 * 2025/01/26 新規作成
 *
 * @author 大連 葉
 */
@Data
@JsonSerialize
public class IfaOrderStatusListA019ApiRequest {

    private String urlId;

    private String patternId;

    private String httpMethod;
}
