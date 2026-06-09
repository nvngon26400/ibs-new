package com.sbisec.helios.gw.common.composite.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class IfaBrandSearchA003ApiRequest {
    
    /** キーワード. */
    @NotEmpty(message = "キーワード")
    @Size(max = 60, message = "キーワード")
    private String search;
    
}
