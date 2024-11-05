package com.thermostate.shared.domain.criteria;

import java.util.List;
import java.util.Map;

public class Filter {
    public final Field field;
    public final Option option;
    public final Value value;

    public Filter(Field field, Option option, Value value) {
        this.field = field;
        this.option = option;
        this.value = value;
    }

    public static List<Filter> listFrom(List<Map<String, String>> filters) {
        return filters.stream().map(filter -> new Filter(new Field(filter.get("field")),
                Option.valueOf(filter.get("option")),
                new Value(filter.get("value")))).toList();
    }

}
