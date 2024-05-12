package com.thermostate.brain.application;

import com.thermostate.shared.events.domain.TemperatureEvent;
import com.thermostate.shared.events.infrastructure.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TemperatureEventsHandler extends EventHandler<TemperatureEvent> {

    private static final Logger logger = LoggerFactory.getLogger(TemperatureEventsHandler.class);

    @Override
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handle(TemperatureEvent event) {
        logger.info("TemperatureEvent reach1: " + event.eventName() + " " + event.eventId());
    }
}
