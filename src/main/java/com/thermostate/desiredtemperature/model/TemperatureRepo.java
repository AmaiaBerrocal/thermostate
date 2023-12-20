package com.thermostate.desiredtemperature.model;

public interface TemperatureRepo {
    Temperature getTemp();
    void updateTemp(Temperature temperature);
}
