package com.thermostate.externaltemperature.model;

import com.thermostate.shared.events.domain.ExternalTemperatureReaded;
import com.thermostate.shared.events.domain.base.AggregateRoot;

public class ExternalTemperature extends AggregateRoot {
    public final String temp;

    public ExternalTemperature(String temp) {
        this.temp = temp;
        record(new ExternalTemperatureReaded(Integer.valueOf(temp)));
    }
}
