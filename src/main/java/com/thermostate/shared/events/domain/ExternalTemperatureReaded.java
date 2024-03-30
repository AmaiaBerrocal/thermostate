package com.thermostate.shared.events.domain;

import com.thermostate.shared.events.domain.base.DomainEvent;
import com.thermostate.shared.events.domain.base.TemperatureEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class ExternalTemperatureReaded extends TemperatureEvent {
    public final Integer temp;
    public ExternalTemperatureReaded(Integer integer) {
        super(UUID.randomUUID().toString());
        this.temp = integer;
    }

    @Override
    public String eventName() {
        return "EXTERNAL_TEMPERATURE_READED";
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
