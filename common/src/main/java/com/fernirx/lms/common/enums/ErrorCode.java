package com.fernirx.lms.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    VALIDATION("ERR_VALIDATION", "Invalid data"),
    NOT_FOUND("ERR_NOT_FOUND", "Resource not found"),
    INVALID_CREDENTIALS("AUTH_INVALID_CREDENTIALS", "Incorrect username or password"),
    UNAUTHORIZED("ERR_UNAUTHORIZED", "Unauthorized access"),
    INTERNAL_ERROR("ERR_INTERNAL", "Internal error"),
    BAD_REQUEST("ERR_BAD_REQUEST", "Bad request");

    private final String code;
    private final String message;
}
