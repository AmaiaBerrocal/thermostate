package com.thermostate.users.domain.event;

import com.thermostate.shared.events.domain.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class UserLoggedIn extends DomainEvent {
    public final String name;
    public UserLoggedIn(String name) {
        super(UUID.randomUUID().toString());
        this.name = name;
    }

    @Override
    public String eventName() {
        return "USER_LOGGED_IN";
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
