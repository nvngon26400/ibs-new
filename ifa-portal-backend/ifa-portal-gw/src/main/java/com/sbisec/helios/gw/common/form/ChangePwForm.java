package com.sbisec.helios.gw.common.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * パスワード変更入力項目
 *
 * @author SCSK
 *
 */
public class ChangePwForm {
    
    private static final String REGEXP = "[a-zA-Z0-9\\+\\-_\\./@\\*#%!\"\\$&\\(\\)=~\\^\\?>\\|`\\[{\\]}:;<´]*";
    
    @NotNull
    @Size(min = 6, max = 32)
    @Pattern(regexp = REGEXP)
    private String oldPassword;
    
    @NotNull
    @Size(min = 6, max = 32)
    @Pattern(regexp = REGEXP)
    private String newPassword;
    
    @NotNull
    @Size(min = 6, max = 32)
    @Pattern(regexp = REGEXP)
    private String newPasswordRepeat;
    
    public String getOldPassword() {
        
        return oldPassword;
    }
    
    public void setOldPassword(String oldPassword) {
        
        this.oldPassword = oldPassword;
    }
    
    public String getNewPassword() {
        
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        
        this.newPassword = newPassword;
    }
    
    public String getNewPasswordRepeat() {
        
        return newPasswordRepeat;
    }
    
    public void setNewPasswordRepeat(String newPasswordRepeat) {
        
        this.newPasswordRepeat = newPasswordRepeat;
    }
}
