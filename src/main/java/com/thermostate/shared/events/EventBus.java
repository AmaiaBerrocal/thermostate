package com.thermostate.shared.events;

import java.util.List;

public interface EventBus {
    void publish(final List<DomainEvent> events);
}
