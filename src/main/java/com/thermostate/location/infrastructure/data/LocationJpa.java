package com.thermostate.location.infrastructure.data;

import com.thermostate.location.domain.Location;
import com.thermostate.location.domain.vo.Latitude;
import com.thermostate.location.domain.vo.Longitude;
import com.thermostate.location.domain.vo.UserId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Locations",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"userId", "createdAt"})
        })
@Getter
@NoArgsConstructor
@IdClass(LocationId.class)
public class LocationJpa {
    @Id
    private String userId;
    @Column
    private Double latitude;
    @Column
    private Double longitude;
    @Id
    private Timestamp createdAt;

    public LocationJpa(String userIdValue,
                       Double latitudeValue,
                       Double longitudeValue) {
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
        this.latitude = latitudeValue;
        this.longitude = longitudeValue;
        this.userId = userIdValue;
    }

    public static LocationJpa fromDomain(Location location) {
        return new LocationJpa(location.getUserIdValue(),
                location.getLatitudeValue(),
                location.getLongitudeValue());
    }

    public Location toDomain() {
        return new Location(UserId.from(UUID.fromString(userId)), Latitude.from(latitude), Longitude.from(longitude));
    }
}
