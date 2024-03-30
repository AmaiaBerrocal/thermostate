package com.thermostate.shared.events.domain;

import com.thermostate.shared.events.domain.base.DomainEvent;
import com.thermostate.shared.events.domain.base.TemperatureEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class RoomTemperatureReaded extends TemperatureEvent {

    public final Integer temp;
    public RoomTemperatureReaded(Integer temp) {
        super(UUID.randomUUID().toString());
        this.temp = temp;
    }
    @Override
    public String eventName() {
        return "ROOM_TEMPERATURE_READED";
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
