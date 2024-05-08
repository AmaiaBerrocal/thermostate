package com.thermostate.brain.domain.events;

import com.thermostate.shared.events.domain.DomainEvent;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;

@RequiredArgsConstructor
public class ThermostateSwitched extends DomainEvent {
    final boolean isOn;

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
}
