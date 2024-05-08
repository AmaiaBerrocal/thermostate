package com.thermostate.brain.application;

import com.thermostate.shared.events.domain.ScheduleEvent;
import com.thermostate.shared.events.infrastructure.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ScheduleEventsHandler implements EventHandler<ScheduleEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleEventsHandler.class);
    @Override
    public void handle(ScheduleEvent event) {
        logger.info("ScheduleEvent reach: " + event.eventName() + " " + event.eventId());
    }
}
