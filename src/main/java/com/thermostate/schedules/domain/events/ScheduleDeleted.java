package com.thermostate.schedules.domain.events;

import com.thermostate.shared.events.domain.DomainEvent;
import com.thermostate.shared.events.domain.ScheduleEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class ScheduleDeleted extends ScheduleEvent {

    UUID id;
    public ScheduleDeleted(UUID id) {
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
