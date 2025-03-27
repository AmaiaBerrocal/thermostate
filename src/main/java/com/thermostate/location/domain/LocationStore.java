package com.thermostate.location.domain;

import com.thermostate.shared.domain.criteria.Criteria;

import java.util.List;

public interface LocationStore {
    void save(Location location);

    List<Location> matching(Criteria criteria);
}
