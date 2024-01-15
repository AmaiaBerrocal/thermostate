package com.thermostate.roomtemperature.model.events;

import com.thermostate.shared.events.domain.DomainEvent;
import com.thermostate.shared.events.domain.TemperatureEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
