package com.thermostate.temperature.model;

public interface TemperatureRepo {
    Temperature getTemp();
    void updateTemp(Temperature temperature);
}
