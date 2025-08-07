package com.fernirx.lms.common.annotations;

import com.fernirx.lms.common.dtos.responses.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(responses = {
        @ApiResponse(
                responseCode = "200",
                description = "Success"
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Bad Request",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                name = "Internal Server Error",
                                value = """
                                            {
                                              "timestamp": "2025-08-04T20:00:00+07:00",
                                              "category": "SERVER",
                                              "message": "Server Error"
                                            }
                                        """
                        )
                )
        )
})
public @interface StandardResponseDoc {
    @AliasFor(annotation = Operation.class, attribute = "summary")
    String value() default "";

    @AliasFor(annotation = Operation.class, attribute = "description")
    String description() default "";
}
