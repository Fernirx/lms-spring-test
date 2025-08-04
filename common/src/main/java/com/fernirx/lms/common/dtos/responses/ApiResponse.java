package com.fernirx.lms.common.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "ApiResponse", description = "Standard API response wrapper")
public class ApiResponse<T> {

    @Schema(description = "Return time", example = "2025-08-04T20:00:00+07:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime timestamp;

    @Schema(description = "HTTP status", example = "200")
    private int status;

    @Schema(description = "Successful or not?", example = "true")
    private Boolean success;

    @Schema(description = "Announcement of results", example = "Success")
    private String message;

    @Schema(description = "Detailed error list")
    private List<ErrorResponse> errors;

    @Schema(description = "Return data")
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .timestamp(ZonedDateTime.now())
                .status(200)
                .success(true)
                .message("Success")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .timestamp(ZonedDateTime.now())
                .status(200)
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .timestamp(ZonedDateTime.now())
                .status(200)
                .success(true)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
                .timestamp(ZonedDateTime.now())
                .status(200)
                .success(true)
                .message("Success")
                .build();
    }

    public static <T> ApiResponse<T> error(int status, String message,  List<ErrorResponse> errors) {
        return ApiResponse.<T>builder()
                .timestamp(ZonedDateTime.now())
                .status(status)
                .success(false)
                .message(message)
                .errors(errors)
                .build();
    }
}
