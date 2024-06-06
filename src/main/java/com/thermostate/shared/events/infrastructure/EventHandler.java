package com.thermostate.shared.events.infrastructure;

import com.thermostate.shared.events.domain.DomainEvent;
import org.springframework.context.ApplicationListener;

public abstract class EventHandler <T extends DomainEvent> implements ApplicationListener<T> {
    abstract public void handle(T event);

    @Override
    public void onApplicationEvent(T event) {
        handle(event);
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
