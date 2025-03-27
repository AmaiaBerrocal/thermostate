package com.thermostate.shared.domain.criteria;

public class Value {
    public final String value;
    private final boolean isAString;

    public Value(String value) {
        this.isAString = !value.matches("[0-9]*");
        if (isAString) {
            this.value = "'" + value + "'";
        } else {
            this.value = value;
        }
    }
}
