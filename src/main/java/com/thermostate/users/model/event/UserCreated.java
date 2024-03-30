package com.thermostate.users.model.event;

import com.thermostate.shared.events.domain.base.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class UserCreated extends DomainEvent {
    public final String name;
    public UserCreated(String name) {
        super(UUID.randomUUID().toString());
        this.name = name;
    }

    @Override
    public String eventName() {
        return "USER_LOGIN_FAILURE";
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
