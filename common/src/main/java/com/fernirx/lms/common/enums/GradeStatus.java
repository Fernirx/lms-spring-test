package com.fernirx.lms.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum GradeStatus {
    PASS("Pass"),
    FAIL("Fail");

    @JsonValue
    private final String value;

    public static GradeStatus fromValue(String value) {
        return Arrays.stream(values())
                .filter(d -> d.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid degree level: " + value));
    }
}
