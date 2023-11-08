package com.thermostate.temperature.model;

import com.thermostate.shared.events.AggregateRoot;
import com.thermostate.temperature.model.event.TargetTemperatureChanged;

public class Temperature extends AggregateRoot {
    private Integer temp;

    public Temperature(Integer temp) {
        this.temp = temp;
    }

    public void change(TemperatureChange temperatureChange) {
        this.temp += temperatureChange.change();
        record(TargetTemperatureChanged.as(temperatureChange.change()));
    }

    public Integer getTemp() {
        return temp;
    }
}
