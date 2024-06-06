package com.thermostate.brain.application;

import com.thermostate.shared.events.domain.ScheduleEvent;
import com.thermostate.shared.events.infrastructure.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ScheduleEventsHandler  extends EventHandler<ScheduleEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleEventsHandler.class);

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)public void handle(ScheduleEvent event) {
        logger.info("ScheduleEvent reach: " + event.eventName() + " " + event.eventId());
    }
}
