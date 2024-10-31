package com.thermostate.location.infrastructure.data;

import com.thermostate.location.domain.Location;
import com.thermostate.location.domain.LocationStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LocationSqliteStorer implements LocationStore {
    LocationJpaRepo repo;
    @Override
    public void save(Location location) {
        repo.save(LocationJpa.fromDomain(location));
    }
}
