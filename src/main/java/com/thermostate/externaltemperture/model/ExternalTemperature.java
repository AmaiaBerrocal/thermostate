package com.thermostate.externaltemperture.model;

import com.thermostate.shared.events.AggregateRoot;

public class ExternalTemperature extends AggregateRoot {
    public final String temp;

    public ExternalTemperature(String temp) {
        this.temp = temp;
    }
}
