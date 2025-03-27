package com.thermostate.externaltemperature.domain;

import com.thermostate.externaltemperature.domain.events.ExternalTemperatureReaded;
import com.thermostate.shared.events.domain.AggregateRoot;

public class ExternalTemperature extends AggregateRoot {
    public final String temp;

    public ExternalTemperature(String temp) {
        this.temp = temp;
        record(new ExternalTemperatureReaded(Integer.valueOf(temp)));
    }
}
