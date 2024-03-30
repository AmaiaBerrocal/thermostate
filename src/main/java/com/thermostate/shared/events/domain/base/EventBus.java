package com.thermostate.shared.events.domain.base;

public interface EventBus {
     <T extends DomainEvent> void emit(T event);
}
