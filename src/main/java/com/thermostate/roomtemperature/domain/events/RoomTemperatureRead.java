package com.thermostate.roomtemperature.domain.events;

import com.thermostate.shared.events.domain.DomainEvent;
import com.thermostate.shared.events.domain.TemperatureEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class RoomTemperatureRead extends TemperatureEvent {

    public final Integer temp;
    public RoomTemperatureRead(Integer temp) {
        super(UUID.randomUUID().toString());
        this.temp = temp;
    }
    @Override
    public String eventName() {
        return "ROOM_TEMPERATURE_READ";
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
