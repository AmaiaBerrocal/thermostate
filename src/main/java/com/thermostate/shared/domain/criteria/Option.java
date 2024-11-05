package com.thermostate.shared.domain.criteria;

public enum Option {
    GT(" >= "),
    LT(" <= "),
    EQ(" = "),
    NOT_EQ(" != ");

    public final String value;

    Option(String value) {
        this.value = value;
    }
}
