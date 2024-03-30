package com.thermostate.targettemperature.model.event;

import com.thermostate.shared.events.domain.base.DomainEvent;
import com.thermostate.shared.events.domain.base.TemperatureEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class TargetTemperatureChanged extends TemperatureEvent {
    public final Integer amount;

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
