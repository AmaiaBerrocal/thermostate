package com.thermostate.location.application;

import com.thermostate.location.domain.Location;
import com.thermostate.location.domain.LocationStore;
import com.thermostate.shared.domain.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GetLocations {
    LocationStore store;

    public List<Location> execute(List<Filter> filters, OrderBy orderBy, Limit limit) {
        if (filters.isEmpty()) {
            return List.of();
        }
        Criteria criteria = new Criteria(filters, orderBy, limit);
        return store.matching(criteria);
    }
}
