package com.thermostate.shared.events.infrastructure;

import com.thermostate.schedules.model.events.EventBus;
import com.thermostate.shared.events.domain.DomainEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Emits all domain events to any DomainEventHandler declared with that event class as type. Many Handlers can be assigned to same class
 * <p>
 * It tries to find handlers for event's superclass when unable to find handlers for that event class, after of this it caches the response (list reference) so
 * any subsequent event of that class will be found.
 * <p>
 * It does not manage order
 */
@Component
public class CustomEventBus implements EventBus {

    private final ApplicationEventPublisher publisher;

    public CustomEventBus(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public <T extends DomainEvent> void emit(T event) {
        publisher.publishEvent(event);
    }
}
