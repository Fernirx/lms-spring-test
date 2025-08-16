package com.fernirx.lms.infrastructure.handler;

import com.fernirx.lms.common.dtos.responses.ErrorDetail;
import com.fernirx.lms.common.dtos.responses.ErrorResponse;
import com.fernirx.lms.common.enums.ErrorCode;
import com.fernirx.lms.common.exceptions.InvalidTokenTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(InvalidTokenTypeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenTypeException(InvalidTokenTypeException ex) {
        log.error("Invalid token type. Expected {} but got {}", ex.getExpectedType(), ex.getTokenType());
        ErrorCode errorCode = ex.getErrorCode();
        ErrorDetail errorDetail = ErrorDetail.of(errorCode);
        ErrorResponse errorResponse = ErrorResponse.of(errorCode.getCategory(), errorCode.getMessage(), errorDetail);
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }
}
