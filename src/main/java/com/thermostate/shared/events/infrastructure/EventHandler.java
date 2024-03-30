package com.thermostate.shared.events.infrastructure;

import com.thermostate.shared.events.domain.base.DomainEvent;

public interface EventHandler <T extends DomainEvent> {
    void handle(T event);
}
