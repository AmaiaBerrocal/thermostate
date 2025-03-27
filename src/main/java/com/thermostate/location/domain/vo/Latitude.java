package com.thermostate.location.domain.vo;

import com.thermostate.shared.domain.ValueObject;

public class Latitude extends ValueObject<Double> {
    public Latitude(Double value) {
        super(value);
    }

    public static Latitude from(double latitude) {
        return new Latitude(latitude);
    }
}
