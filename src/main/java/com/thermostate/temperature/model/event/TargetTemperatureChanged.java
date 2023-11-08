package com.thermostate.temperature.model.event;

import com.thermostate.shared.events.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class TargetTemperatureChanged extends DomainEvent {
    private Integer amount;

    public TargetTemperatureChanged(Integer amount) {
        super(UUID.randomUUID().toString());
        this.amount = amount;
    }

    public static TargetTemperatureChanged as(Integer amount) {
        return new TargetTemperatureChanged(amount);
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
