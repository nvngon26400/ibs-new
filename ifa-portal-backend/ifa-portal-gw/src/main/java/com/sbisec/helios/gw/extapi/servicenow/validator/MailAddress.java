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
 * メールアドレス Validator
 *
 * @author SCSK
 */
@Constraint(validatedBy = {})
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Size(max = 127, message = "メールアドレス")
@Pattern(regexp = "\\S+@\\S+\\.\\S+", message = "メールアドレス")
public @interface MailAddress {
    
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
