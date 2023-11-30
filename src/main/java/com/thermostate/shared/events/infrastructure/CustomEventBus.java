package com.thermostate.shared.events.infrastructure;

import com.thermostate.schedules.model.events.EventBus;
import com.thermostate.shared.events.domain.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomEventBus.class);
    public Map<String, List<EventHandler<? extends DomainEvent>>> eventSubscribers = new ConcurrentHashMap<>();

    public CustomEventBus(Collection<EventHandler<? extends DomainEvent>> eventHandlerList) {
        eventHandlerList.forEach(this::subscribe);
    }

    private static <T extends DomainEvent> void handleEvent(T event, EventHandler<T> subscriber) {
        try {
            subscriber.handle(event);
        } catch (Exception e) {
            LOGGER.error("Error when trying to manage event " + event.toString() + " with handler " + subscriber, e);
        }
    }

    private void subscribe(EventHandler<?> eventHandler) {
        String clazzName = obtainClazz(eventHandler);
        List<EventHandler<?>> subscribers = eventSubscribers.computeIfAbsent(clazzName, k -> new ArrayList<>());
        if (!subscribers.contains(eventHandler)) {
            subscribers.add(eventHandler);
        }
    }

    private String obtainClazz(EventHandler<? extends DomainEvent> eventHandler) {
        Class<?> clazz = eventHandler.getClass();

        Type[] genericInterfaces = clazz.getGenericInterfaces();

        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType parameterizedType) {
                if (parameterizedType.getRawType() == EventHandler.class) {
                    Type[] typeArguments = parameterizedType.getActualTypeArguments();
                    return typeArguments[0].getTypeName();
                }
            }
        }
        throw new RuntimeException("Not found event handle for " + eventHandler);
    }

    public <T extends DomainEvent> void emit(T event) {
        eventSubscribers.computeIfAbsent(event.getClass().getCanonicalName(), k -> getEventSubscribers(event))
        .forEach(subscriber -> handleEvent(event, (EventHandler<T>) subscriber));
    }

    private <T extends DomainEvent> List<EventHandler<? extends DomainEvent>> getEventSubscribers(T event) {
        Class<?> clazz = event.getClass();
        Class<?> superClazz = clazz.getSuperclass();
        while (superClazz != null) {
            if (eventSubscribers.containsKey(superClazz.getCanonicalName())) {
                return eventSubscribers.get(superClazz.getCanonicalName());
            }
            superClazz = superClazz.getSuperclass();
        }
        LOGGER.warn("Warning!: No handler found for " + clazz);
        return Collections.emptyList();
    }
}
