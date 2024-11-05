package com.thermostate.shared.domain.criteria;

import java.util.List;
import java.util.stream.Collectors;

public record Criteria (List<Filter> filters, OrderBy orderBy, Limit limit) {
    public String toSql() {
        String filters =  this.filters.stream()
                .map(this::filterToSql)
                .collect(Collectors.joining(" and "));
        String order = this.orderBy == null ? "" : this.orderBy.toString();
        String limit = this.limit == null ? "" : this.limit.toSql();
        return filters + order + limit;
    }

    public String filterToSql(Filter filter) {
        return filter.field.field +
                filter.option.value +
                filter.value.value;
    }
}
