package com.fernirx.lms.common.annotations.validations;

import com.fernirx.lms.common.constants.ValidationConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.isBlank() || value.matches(ValidationConstants.PHONE_PATTERN);
    }
}
