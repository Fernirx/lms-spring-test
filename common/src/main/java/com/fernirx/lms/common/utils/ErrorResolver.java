package com.fernirx.lms.common.utils;

import com.fernirx.lms.common.dtos.responses.ErrorDetail;
import com.fernirx.lms.common.enums.ErrorCode;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class ErrorResolver {

    public static Optional<ErrorCode> getHighestPriorityErrorCode(List<ErrorDetail> details) {
        return details.stream()
                .map(ErrorResolver::mapDetailToErrorCode)
                .flatMap(Optional::stream)
                .max(Comparator.comparingInt(ErrorCode::getLevel));
    }

    private static Optional<ErrorCode> mapDetailToErrorCode(ErrorDetail detail) {
        return Optional.ofNullable(detail.getCode());
    }

    private ErrorResolver() {
        throw new UnsupportedOperationException("Utility class");
    }
}
