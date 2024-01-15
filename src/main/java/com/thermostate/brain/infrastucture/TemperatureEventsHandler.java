package com.thermostate.brain.infrastucture;

import com.thermostate.shared.events.infrastructure.EventHandler;
import com.thermostate.shared.events.domain.TemperatureEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TemperatureEventsHandler implements EventHandler<TemperatureEvent> {

    private static final Logger logger = LoggerFactory.getLogger(TemperatureEventsHandler.class);
    @Override
    public void handle(TemperatureEvent event) {
        logger.info("TemperatureEvent reach1: " + event.eventName() + " " + event.eventId());
    }
}
