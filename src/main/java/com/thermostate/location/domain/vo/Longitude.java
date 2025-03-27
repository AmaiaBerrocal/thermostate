package com.thermostate.location.domain.vo;

import com.thermostate.shared.domain.ValueObject;

public class Longitude extends ValueObject<Double> {
    public Longitude(Double value) {
        super(value);
    }

    public static Longitude from(double longitude) {
        return new Longitude(longitude);
    }
}
