package com.thermostate.shared.events.domain.base;


public abstract class TemperatureEvent extends DomainEvent {
    public TemperatureEvent(String string) {
        super(string);
    }
}
