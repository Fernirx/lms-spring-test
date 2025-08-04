package com.fernirx.lms.common.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime timestamp;
    private int status;
    private Boolean success;
    private String message;
    private List<ErrorResponse> errors;
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
