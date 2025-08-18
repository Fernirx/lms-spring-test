package com.fernirx.lms.infrastructure.handler;

import com.fernirx.lms.common.dtos.responses.ErrorDetail;
import com.fernirx.lms.common.dtos.responses.ErrorResponse;
import com.fernirx.lms.common.enums.ErrorCategory;
import com.fernirx.lms.common.enums.ErrorCode;
import com.fernirx.lms.common.exceptions.LmsException;
import com.fernirx.lms.common.exceptions.ResourceNotFoundException;
import com.fernirx.lms.common.exceptions.ValidationException;
import com.fernirx.lms.common.utils.ErrorResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LmsException.class)
    public ResponseEntity<ErrorResponse> handleLmsException(LmsException ex) {
        log.error(ex.getMessage(), ex);
        return buildErrorResponse(ex.getErrorCode());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return buildErrorResponse(ex.getErrorCode());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        log.error(ex.getMessage(), ex);
        return buildErrorResponse(ex.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        List<ErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    Optional<ErrorCode> optional = ErrorCode.fromCode(fieldError.getDefaultMessage());
                    ErrorCode errorCode = optional.orElse(ErrorCode.REQUIRED_FIELD);
                    return ErrorDetail.of(errorCode, errorCode.getMessage(), fieldError.getField());
                }).toList();
        return buildErrorResponse(details);
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

    public ResponseEntity<ErrorResponse> buildErrorResponse(List<ErrorDetail> details) {
        Optional<ErrorCode> optionalErrorCode = ErrorResolver.getHighestPriorityErrorCode(details);

        ErrorCategory category = optionalErrorCode
                .map(ErrorCode::getCategory)
                .orElse(ErrorCategory.SERVER);

        HttpStatus status = optionalErrorCode
                .map(ErrorCode::getHttpStatus)
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);

        ErrorResponse errorResponse = ErrorResponse.of(
                category,
                category.getMessage(),
                details
        );
        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }
}
