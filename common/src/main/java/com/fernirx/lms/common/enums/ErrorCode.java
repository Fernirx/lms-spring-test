package com.fernirx.lms.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // System errors
    METHOD_NOT_ALLOWED("ERR_METHOD_NOT_ALLOWED", "HTTP method not allowed"),
    TOO_MANY_REQUESTS("ERR_TOO_MANY_REQUESTS", "Too many requests"),
    SERVICE_UNAVAILABLE("ERR_SERVICE_UNAVAILABLE", "Service unavailable"),

    // Validation errors
    REQUIRED_FIELD("ERR_REQUIRED", "This field is required"),
    INVALID_EMAIL("ERR_INVALID_EMAIL", "Invalid email format"),
    VALUE_TOO_SHORT("ERR_TOO_SHORT", "Value must be at least {min} characters"),

    // Auth errors
    INVALID_CREDENTIALS("AUTH_INVALID_CREDENTIALS", "Invalid username or password"),
    EXPIRED_TOKEN("AUTH_EXPIRED_TOKEN", "Token has expired"),
    ACCESS_DENIED("AUTH_ACCESS_DENIED", "Access denied"),

    // Resource errors
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found"),
    COURSE_NOT_FOUND("COURSE_NOT_FOUND", "Course not found"),
    FILE_NOT_FOUND("FILE_NOT_FOUND", "File not found");

    private final String code;
    private final String message;
}
