package com.thermostate.schedules.model.events;

import com.thermostate.shared.events.domain.DomainEvent;

public interface EventBus {
     <T extends DomainEvent> void emit(T event);
}
