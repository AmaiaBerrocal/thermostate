package com.thermostate.shared.domain.criteria;

import static org.assertj.core.api.Assertions.assertThat;

public class Limit {
    final Integer limit;

    public Limit(Integer limit) {
        this.limit = limit;
    }

    public String toSql() {
        if (limit == null) {
            return "";
        }
        return " limit " + limit;
    }
}
