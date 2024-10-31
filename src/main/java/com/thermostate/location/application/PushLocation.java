package com.thermostate.location.application;

import com.thermostate.location.domain.Location;
import com.thermostate.location.domain.LocationStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PushLocation {
    LocationStore store;
    public void execute(Location location) {
        store.save(location);
    }
}
