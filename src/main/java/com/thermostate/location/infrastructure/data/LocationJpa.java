package com.thermostate.location.infrastructure.data;

import com.thermostate.location.domain.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Location")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationJpa {
    @Id
    private String userId;
    @Column
    private Double latitude;
    @Column
    private Double longitude;
    @Column
    private Timestamp createdAt;

    public static LocationJpa fromDomain(Location location) {
        return new LocationJpa(location.getUserIdValue(),
                location.getLatitudeValue(),
                location.getLongitudeValue(),
                Timestamp.valueOf(LocalDateTime.now()));
    }
}
