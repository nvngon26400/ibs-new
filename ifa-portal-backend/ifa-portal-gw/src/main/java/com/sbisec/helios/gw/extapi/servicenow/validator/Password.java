package com.sbisec.helios.gw.extapi.servicenow.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * パスワード Validator
 *
 * @author SCSK
 */
@Constraint(validatedBy = {})
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Size(min = 6, max = 32, message = "パスワード")
@Pattern(regexp = "[a-zA-Z0-9\\+\\-_\\./@\\*#%!\"\\$&\\(\\)=~\\^\\?>\\|`\\[{\\]}:;<´]*", message = "パスワード")
public @interface Password {
    
    /**
     * メッセージ
     */
    String message() default "";
    
    /**
     * グループ
     */
    Class<?>[] groups() default {};
    
    /**
     * ペイロード 
     */
    Class<? extends Payload>[] payload() default {};
    
}
