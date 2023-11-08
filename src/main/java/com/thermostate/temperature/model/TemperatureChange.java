package com.thermostate.temperature.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TemperatureChange {
    public final Integer change;
    public static TemperatureChange create(Integer amount) {
        return new TemperatureChange(amount);
    }
}
