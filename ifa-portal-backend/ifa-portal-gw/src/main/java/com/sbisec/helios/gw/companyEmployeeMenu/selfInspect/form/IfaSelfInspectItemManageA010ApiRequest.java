package com.sbisec.helios.gw.companyEmployeeMenu.selfInspect.form;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * 画面ID：SUB0506_02-01
 * 画面名：自己点検項目管理
 * 2024/06/19 新規作成
 *
 * @author SCSK 江口
 */
@Data
public class IfaSelfInspectItemManageA010ApiRequest {

    /** 自己点検項目ID（数字） */
    @Digits(integer = 8, fraction = 0, message = "自己点検項目ID")
    @Pattern(regexp = "0-9", message = "自己点検項目ID")
    @Size(max = 8, message = "自己点検項目ID")
    private String selfCheckItemId;

    /** 業者区分（全角半角） */
    @NotEmpty(message = "業者区分")
    @Size(max = 20, message = "業者区分")
    private String brokerType;

}
