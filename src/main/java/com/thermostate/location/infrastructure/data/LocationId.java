package com.thermostate.location.infrastructure.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationId implements Serializable {
    private String userId;
    private Timestamp createdAt;
}