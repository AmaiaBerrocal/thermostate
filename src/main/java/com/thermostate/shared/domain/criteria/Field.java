package com.thermostate.shared.domain.criteria;

import static org.assertj.core.api.Assertions.assertThat;

public class Field {
    String field;

    public Field(String field) {
        assertThat(field).isNotBlank();
        this.field = field;
    }

    public Field from(String field) {
        return new Field(field);
    }
}
