package org.naemansan.common.annotaion;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.naemansan.common.constant.DateValidator;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface Date {
    String message() default "Invalid Date Format. (yyyy-MM-dd)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
