package com.thermostate.shared.domain.criteria;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderBy {
    Field field;
    Order order;

    public OrderBy(Field field, Order order) {
        this.field = field;
        this.order = order;
        assertThat(order).isNotNull();
        assertThat(field).isNotNull();
    }

    public static OrderBy from(Map<String, String> orderBy) {
        return new OrderBy(new Field(orderBy.get("field")), Order.valueOf(orderBy.get("order")));
    }

    public String toString() {
        return new StringBuilder()
                .append(" ORDER BY ")
                .append(field.field)
                .append(order.value)
                .toString();
    }
}
