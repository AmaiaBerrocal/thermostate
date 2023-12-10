package com.thermostate.brain.infrastucture;

import com.thermostate.shared.events.infrastructure.EventHandler;
import com.thermostate.shared.events.domain.TemperatureEvent;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

@Component
public class TemperatureEventsHandler implements EventHandler<TemperatureEvent> {
    @Override
    public void handle(TemperatureEvent event) {
        System.out.println("TemperatureEvent reach: " + event.eventName() + " " + event.eventId());
    }
}
