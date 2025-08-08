package com.fernirx.lms.common.annotations.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PhoneValidator.class)
public @interface ValidPhone {
    String message() default "phone.invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
