package com.thermostate.roomtemperature.model.events;

import com.thermostate.shared.events.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class RoomTemperatureReaded extends DomainEvent {

    Integer id;
    public RoomTemperatureReaded(Integer id) {
        super(UUID.randomUUID().toString());
        this.id = id;
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
