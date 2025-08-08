package com.fernirx.lms.common.utils;

import com.fernirx.lms.common.dtos.responses.ErrorDetail;
import com.fernirx.lms.common.enums.ErrorCategory;
import com.fernirx.lms.common.enums.ErrorCode;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class ErrorCategoryResolver {

    private ErrorCategoryResolver() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static ErrorCategory resolveFrom(List<ErrorDetail> details) {
        return details.stream()
                .map(ErrorCategoryResolver::mapCodeToCategory)
                .min(Comparator.comparingInt(ErrorCategory::getPriority))
                .orElse(ErrorCategory.SERVER);
    }

    private static ErrorCategory mapCodeToCategory(ErrorDetail detail) {
        return Optional.ofNullable(detail.getCode())
                .map(ErrorCode::getCategory)
                .orElse(ErrorCategory.SERVER);
    }
}
