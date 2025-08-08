package com.fernirx.lms.common.annotations.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {
    private static final String E164_REGEX = "^\\+[1-9]\\d{1,14}$";  // Strict E.164

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.isBlank() || value.matches(E164_REGEX);
    }
}
