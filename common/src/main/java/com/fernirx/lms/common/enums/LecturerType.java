package com.fernirx.lms.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum LecturerType {
    FULL_TIME("FullTime"),
    VISITING("Visiting"),
    ADJUNCT("Adjunct"),
    CONTRACTUAL("Contractual");

    @JsonValue
    private final String value;

    public static LecturerType fromValue(String value) {
        return Arrays.stream(values())
                .filter(d -> d.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid degree level: " + value));
    }
}
