package com.thermostate.shared.domain;

import com.thermostate.shared.events.domain.AggregateRoot;
import com.thermostate.targettemperature.model.event.TargetTemperatureChanged;

public class Temperature extends AggregateRoot {
    private Integer temp;

    public Temperature(Integer temp) {
        this.temp = temp;
    }

    public static Temperature of(Integer temp) {
        return new Temperature(temp);
    }

    public Integer getTemp() {
        return temp;
    }
}
