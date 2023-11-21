package com.thermostate.users.model.event;

import com.thermostate.shared.events.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;

public class UserLoginFailure extends DomainEvent {
    public final String name;
    public UserLoginFailure(String name) {
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
