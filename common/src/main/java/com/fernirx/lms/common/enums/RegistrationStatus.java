package com.fernirx.lms.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum RegistrationStatus {
    REGISTERED("Registered"),
    CANCELLED("Cancelled");

    @JsonValue
    private final String value;

    public static RegistrationStatus fromValue(String value) {
        return Arrays.stream(values())
                .filter(d -> d.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid degree level: " + value));
    }
}
