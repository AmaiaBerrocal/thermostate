package com.thermostate.brain.infrastucture;

import com.thermostate.shared.events.domain.ScheduleEvent;
import com.thermostate.shared.events.infrastructure.EventHandler;
import com.thermostate.shared.events.domain.TemperatureEvent;
import org.springframework.stereotype.Component;

@Component
public class ScheduleEventsHandler implements EventHandler<ScheduleEvent> {
    @Override
    public void handle(ScheduleEvent event) {
        System.out.println("ScheduleEvent reach: " + event.eventName() + " " + event.eventId());
    }
}
