package com.thermostate.shared.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueObject<T> {
    protected final T value;

    public ValueObject(T value) {
        assertThat(value).isNotNull();
        this.value = value;
    }

    public T value() {
        return value;
    }
}
