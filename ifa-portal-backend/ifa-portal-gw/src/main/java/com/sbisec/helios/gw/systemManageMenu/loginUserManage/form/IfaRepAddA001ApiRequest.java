package com.sbisec.helios.gw.systemManageMenu.loginUserManage.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * @author SCSK
 *
 */
@Data
public class IfaRepAddA001ApiRequest {
    
    /** ログインID. */
    @NotEmpty(message = "ログインID")
    @Size(max = 16, message = "ログインID")
    private String loginId;
    
    /** 権限コード（全角半角）. */
    @NotEmpty(message = "権限コード")
    @Size(max = 16, message = "権限コード")
    private String privId;
    
}
