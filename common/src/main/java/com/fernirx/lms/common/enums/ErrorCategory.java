package com.fernirx.lms.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCategory {
    AUTHENTICATION("Authentication Error", HttpStatus.UNAUTHORIZED, 1),
    AUTHORIZATION("Authorization Error", HttpStatus.FORBIDDEN, 2),
    NOT_FOUND("Resource Not Found", HttpStatus.NOT_FOUND, 3),
    CONFLICT("Conflict Error", HttpStatus.CONFLICT, 4),
    VALIDATION("Validation Error", HttpStatus.BAD_REQUEST, 5),
    RATE_LIMITING("Rate Limiting Error", HttpStatus.TOO_MANY_REQUESTS, 6),
    METHOD_NOT_ALLOWED("Method Not Allowed", HttpStatus.METHOD_NOT_ALLOWED, 7),
    SERVER("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, 100);

    private final String description;
    private final HttpStatus httpStatus;
    private final int priority;
}
