package com.sbisec.helios.gw.common.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * メールアドレス入力項目
 *
 * @author SCSK
 *
 */
@Data
public class AddEmailForm {
    
    /**
     * ユーザID
     */
    @NotNull
    @Size(min = 6, max = 16)
    @Pattern(regexp = "[a-zA-Z0-9\\+\\-_\\./@\\*#%]*")
    private String userId;
    
    /**
     * パスワード
     */
    @NotNull
    @Size(min = 6, max = 32)
    @Pattern(regexp = "[a-zA-Z0-9\\+\\-_\\./@\\*#%!\"\\$&\\(\\)=~\\^\\?>\\|`\\[{\\]}:;<´]*")
    private String password;
    
    /**
     * メールアドレス
     */
    @NotNull
    @Pattern(regexp = "\\S+@\\S+\\.\\S+")
    private String mailAddress;
    
}
