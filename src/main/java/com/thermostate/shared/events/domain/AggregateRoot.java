package com.thermostate.shared.events.domain;

import com.thermostate.schedules.model.events.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot {
    private List<DomainEvent> domainEvents = new ArrayList<>();

    private List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = domainEvents;
        domainEvents = Collections.emptyList();
        return events;
    }

    final public void publishEventsIn(EventBus eventBus) {
        pullDomainEvents().forEach(eventBus::emit);
    }

    final protected void record(DomainEvent event) {
        domainEvents.add(event);
    }
}
