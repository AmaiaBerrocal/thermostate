package com.thermostate.targettemperature.model;

import com.thermostate.shared.events.domain.AggregateRoot;
import com.thermostate.targettemperature.model.event.ManualTemperatureChanged;

public class TemperatureChange extends AggregateRoot {
    Integer change;

    public TemperatureChange(Integer amount) {
        super();
        record(ManualTemperatureChanged.as(amount));
    }

    public static TemperatureChange create(Integer amount) {
        return new TemperatureChange(amount);
    }
}
