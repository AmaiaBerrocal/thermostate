package com.thermostate.temperature.model;

import com.thermostate.shared.events.domain.AggregateRoot;
import com.thermostate.temperature.model.event.TargetTemperatureChanged;

public class Temperature extends AggregateRoot {
    private Integer temp;

    public Temperature(Integer temp) {
        this.temp = temp;
    }

    public void change(TemperatureChange temperatureChange, TemperatureRepo temperatureRepo) {
        this.temp += temperatureChange.change();
        temperatureRepo.updateTemp(this);
        record(TargetTemperatureChanged.as(temperatureChange.change()));
    }

    public Integer getTemp() {
        return temp;
    }
}
