package com.thermostate.location.application;

import com.thermostate.location.domain.Location;
import com.thermostate.location.domain.LocationStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class PushLocation {
    final LocationStore store;

    public PushLocation(LocationStore store) {
        this.store = store;
    }

    public void execute(Location location) {
        location.save(store);
    }
}
