package com.thermostate.desiredtemperature.model;

public record TemperatureChange(Integer change){
    public static TemperatureChange create(Integer amount) {
        return new TemperatureChange(amount);
    }
}
