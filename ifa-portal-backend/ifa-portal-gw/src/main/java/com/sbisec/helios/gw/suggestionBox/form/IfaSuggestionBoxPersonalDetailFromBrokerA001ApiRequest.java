package com.sbisec.helios.gw.suggestionBox.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;


/**
 * 画面ID：SUB0511_01-02
 * 画面名：仲介業者からの要望詳細
 *
 * @author SCSK山岸
 2025/07/25 新規作成
 */
@Data
public class IfaSuggestionBoxPersonalDetailFromBrokerA001ApiRequest {

    /** 要望No（数字） */
    @Size(max = 6, message = "要望No")
    @Pattern(regexp = "[0-9]*", message = "要望No")
    @NotEmpty(message = "要望No")
    private String sbpNo;

}
