package com.thermostate.schedules.model.events;

import com.thermostate.shared.events.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class ScheduleDeleted extends DomainEvent {

    Integer id;
    public ScheduleDeleted(Integer id) {
        super(UUID.randomUUID().toString());
        this.id = id;
    }
    @Override
    public String eventName() {
        return "SCHEDULE_DELETED";
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
