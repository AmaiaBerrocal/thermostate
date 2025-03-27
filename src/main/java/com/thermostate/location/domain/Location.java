package com.thermostate.location.domain;

import com.thermostate.location.domain.vo.Latitude;
import com.thermostate.location.domain.vo.Longitude;
import com.thermostate.location.domain.vo.UserId;
import lombok.Value;

import java.util.UUID;

@Value
public class Location {
    UserId userId;
    Latitude latitude;
    Longitude longitude;

    public String getUserIdValue() {
        return userId.value().toString();
    }

    public Double getLatitudeValue() {
        return latitude.value();
    }

    public Double getLongitudeValue() {
        return longitude.value();
    }

    public static Location from(UUID userID, Double latitude, Double longitude) {
        return new Location(UserId.from(userID), Latitude.from(latitude), Longitude.from(longitude));
    }

    public void save(LocationStore store) {
        store.save(this);
    }
}
