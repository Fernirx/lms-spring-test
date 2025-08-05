package com.fernirx.lms.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCategory {
    VALIDATION("Validation Error", HttpStatus.BAD_REQUEST),
    AUTHENTICATION("Authentication Error", HttpStatus.UNAUTHORIZED),
    AUTHORIZATION("Authorization Error", HttpStatus.FORBIDDEN),
    NOT_FOUND("Resource Not Found", HttpStatus.NOT_FOUND),
    CONFLICT("Conflict Error", HttpStatus.CONFLICT),
    SERVER("Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
    RATE_LIMITING("Rate Limiting Error", HttpStatus.TOO_MANY_REQUESTS),
    METHOD_NOT_ALLOWED("Method Not Allowed", HttpStatus.METHOD_NOT_ALLOWED);


    private final String description;
    private final HttpStatus httpStatus;
}
