package com.thermostate.shared.events.domain;

import org.springframework.context.ApplicationEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class DomainEvent extends ApplicationEvent {
    private String aggregateId;
    private String eventId;

    public DomainEvent(String aggregateId) {
        super(aggregateId);
        this.aggregateId = aggregateId;
        this.eventId     = UUID.randomUUID().toString();
    }

    public DomainEvent(String aggregateId, String eventId, String occurredOn) {
        super(aggregateId);
        this.aggregateId = aggregateId;
        this.eventId     = eventId;
    }

    public abstract String eventName();

    public abstract Map<String, Serializable> toPrimitives();

    public abstract DomainEvent fromPrimitives(
        String aggregateId,
        HashMap<String, Serializable> body,
        String eventId,
        String occurredOn
    );

    public String aggregateId() {
        return aggregateId;
    }

    public String eventId() {
        return eventId;
    }

}
