package com.sbisec.helios.gw.systemManageMenu.loginUserManage.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class IfaLoginIdUpdateRegisterA014DeleteCcsDataApiRequest {
    
    /** ログインID. */
    @NotEmpty(message = "ログインID")
    @Size(max = 16, message = "ログインID")
    private String loginId;
    
}
