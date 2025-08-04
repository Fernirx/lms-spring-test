package com.fernirx.lms.common.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fernirx.lms.common.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "ApiResponse", description = "Standard API response wrapper")
public class ErrorResponse {

    @Schema(description = "Error Code", example = "ERR_VALIDATION")
    private String code;

    @Schema(description = "Error message", example = "Username is required")
    private String message;

    @Schema(description = "Field name error", example = "username")
    private String field;

    public static ErrorResponse of(String code, String message) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .build();
    }

    public static ErrorResponse of(String code, String message, String field) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .field(field)
                .build();
    }

    public static ErrorResponse of(ErrorCode  errorCode) {
        return of(errorCode.getCode(), errorCode.getMessage());
    }

    public static ErrorResponse of(ErrorCode  errorCode, String field) {
        return of(errorCode.getCode(), errorCode.getMessage(),  field);
    }
}
