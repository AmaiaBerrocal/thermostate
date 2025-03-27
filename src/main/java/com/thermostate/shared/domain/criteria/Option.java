package com.thermostate.shared.domain.criteria;

import org.springframework.util.StringUtils;

import java.util.Arrays;

public enum Option {
    GT(" >= "),
    LT(" <= "),
    EQ(" = "),
    NOT_EQ(" != ");

    public final String value;

    Option(String value) {
        this.value = value;
    }

    public static Option fromValue(String value) {
        return Arrays.stream(Option.values()).filter(it -> it.value.trim().equals(value.trim())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No " + value + " in Option"));
    }
}
