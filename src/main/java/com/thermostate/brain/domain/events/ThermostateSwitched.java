package com.thermostate.brain.domain.events;

import com.thermostate.shared.events.domain.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;

public class ThermostateSwitched extends DomainEvent {
    final boolean isOn;

    public ThermostateSwitched(boolean isOn) {
        super(Boolean.toString(isOn));
        this.isOn = isOn;
    }

    public static ThermostateSwitched of(Boolean active) {
        return new ThermostateSwitched(active);
    }

    @Override
    public String eventName() {
        return "THERMOSTATE_SWITCHED";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return null;
    }

    @Override
    public DomainEvent fromPrimitives(String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn) {
        return null;
    }

    public boolean isOn() {
        return isOn;
    }
}
