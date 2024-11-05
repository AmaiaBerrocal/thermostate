package com.thermostate.location.infrastructure;

import com.thermostate.location.application.GetLocations;
import com.thermostate.location.application.PushLocation;
import com.thermostate.location.domain.Location;
import com.thermostate.shared.domain.criteria.Filter;
import com.thermostate.shared.domain.criteria.Limit;
import com.thermostate.shared.domain.criteria.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor
public class LocationController {
    private final PushLocation pushLocation;
    private final GetLocations getLocations;

    @PutMapping("/geolocation")
    public void putLocation(@RequestBody LocationRequest request) {
        pushLocation.execute(Location.from(
                request.userId,
                request.latitude,
                request.longitude
        ));
    }

    @PostMapping("/geolocation")
    public List<LocationResponse> getLocations(@RequestBody LocationSearchRequest request) {
        return getLocations.execute(
           Filter.listFrom(request.filters), OrderBy.from(request.orderBy), new Limit(request.limit)).stream()
                .map(it -> new LocationResponse(
                        it.getUserIdValue(),
                        it.getLatitudeValue(),
                        it.getLongitudeValue()))
                .toList();
    }

    @Value
    public static class LocationResponse {
        String userId;
        Double latitude;
        Double longitude;
    }

}

@AllArgsConstructor
class LocationRequest {
    UUID userId;
    Double latitude;
    Double longitude;
}

@AllArgsConstructor
class LocationSearchRequest {
    List<Map<String,String>> filters;
    Map<String, String> orderBy;
    Integer limit;
}

