package com.thermostate.targettemperature.domain.event;

import com.thermostate.shared.events.domain.DomainEvent;
import com.thermostate.shared.events.domain.TemperatureEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class ManualTemperatureChanged extends TemperatureEvent {
    public final Integer amount;

    public ManualTemperatureChanged(Integer amount) {
        super(UUID.randomUUID().toString());
        this.amount = amount;
    }

    public static ManualTemperatureChanged as(Integer amount) {
        return new ManualTemperatureChanged(amount);
    }

    @Override
    public String eventName() {
        return "TARGET_TEMPERATURE_CHANGED";
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
