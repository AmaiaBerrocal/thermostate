package com.thermostate.shared.domain.criteria;

import static org.assertj.core.api.Assertions.assertThat;

public class Limit {
    final Integer limit;

    public Limit(Integer limit) {
        assertThat(limit).isNotNull();
        assertThat(limit).isPositive();
        this.limit = limit;
    }

    public String toSql() {
        return " limit " + limit;
    }
}
