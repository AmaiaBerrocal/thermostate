package com.thermostate.location.infrastructure;

import com.thermostate.location.application.PushLocation;
import com.thermostate.location.domain.Location;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor
public class LocationController {
    private final PushLocation pushLocation;

    @PutMapping("/geolocation")
    public void putLocation(@RequestBody LocationRequest request) {
        pushLocation.execute(Location.from(
                request.userId,
                request.latitude,
                request.longitude
        ));
    }
}

class LocationRequest {
    UUID userId;
    Double latitude;
    Double longitude;
}