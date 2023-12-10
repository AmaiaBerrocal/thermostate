package com.thermostate.shared.events.domain;


public abstract class ScheduleEvent extends DomainEvent {
    public ScheduleEvent(String string) {
        super(string);
    }
}
