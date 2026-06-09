package com.sbisec.helios.gw.brokerageMenu.customerMenu.form;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class IfaHoldingSecurityListA019ApiRequest {
    @NotEmpty(message = "協会コード")
    /** 協会コード. */
    private String fundCode;
}
