package com.thermostate.shared.domain.criteria;

import org.springframework.util.StringUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderBy {
    Field field;
    Order order;
    private static OrderBy NO_ORDER = new OrderBy(null, null);

    private OrderBy(Field field, Order order) {
        this.field = field;
        this.order = order;
    }

    public static OrderBy from(Map<String, String> orderBy) {
        if (orderBy == null || orderBy.isEmpty()) {
            return NO_ORDER;
        }
        return new OrderBy(new Field(orderBy.get("field")), Order.valueOf(orderBy.get("order")));
    }

    public String toString() {
        if (this == NO_ORDER) {
            return "";
        }
        return new StringBuilder()
                .append(" ORDER BY ")
                .append(field.field)
                .append(order.value)
                .toString();
    }
}
