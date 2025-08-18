package com.fernirx.lms.infrastructure.handler;

import com.fernirx.lms.common.dtos.responses.ErrorDetail;
import com.fernirx.lms.common.dtos.responses.ErrorResponse;
import com.fernirx.lms.common.enums.ErrorCategory;
import com.fernirx.lms.common.enums.ErrorCode;
import com.fernirx.lms.common.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException ex) {
        log.error("Invalid token (signature/illegal argument)", ex);
        return buildErrorResponse(ex.getErrorCode());
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<ErrorResponse> handleJwtValidationException(JwtValidationException ex) {
        log.error("Unexpected JWT validation error", ex);
        return buildErrorResponse(ex.getErrorCode());
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ErrorResponse> handleExpiredTokenException(ExpiredTokenException ex) {
        log.warn("Token has expired", ex);
        return buildErrorResponse(ex.getErrorCode());
    }

    @ExceptionHandler(InvalidTokenTypeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenTypeException(InvalidTokenTypeException ex) {
        log.error("Invalid token type. Expected {} but got {}", ex.getExpectedType(), ex.getTokenType(), ex);
        return buildErrorResponse(ex.getErrorCode());
    }

    @ExceptionHandler(MalformedTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidMalformedTokenException(MalformedTokenException ex) {
        log.error("Malformed token", ex);
        return buildErrorResponse(ex.getErrorCode());
    }

    @ExceptionHandler(UnsupportedTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUnsupportedTokenException(UnsupportedTokenException ex) {
        log.error("Unsupported token", ex);
        return buildErrorResponse(ex.getErrorCode());
    }

    public ResponseEntity<ErrorResponse> buildErrorResponse(ErrorCode errorCode) {
        ErrorDetail detail = ErrorDetail.of(errorCode, errorCode.getMessage());
        ErrorCategory category = errorCode.getCategory();

        ErrorResponse errorResponse = ErrorResponse.of(
                category,
                category.getMessage(),
                detail
        );
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }
}
