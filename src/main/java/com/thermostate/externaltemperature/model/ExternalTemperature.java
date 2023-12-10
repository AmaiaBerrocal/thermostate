package com.thermostate.externaltemperature.model;

import com.thermostate.externaltemperature.model.events.ExternalTemperatureReaded;
import com.thermostate.shared.events.domain.AggregateRoot;

public class ExternalTemperature extends AggregateRoot {
    public final String temp;

    public ExternalTemperature(String temp) {
        this.temp = temp;
        record(new ExternalTemperatureReaded(Integer.valueOf(temp)));
    }
}
