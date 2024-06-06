package com.thermostate.brain.application;

import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.externaltemperature.model.events.ExternalTemperatureReaded;
import com.thermostate.shared.domain.Temperature;
import com.thermostate.shared.events.infrastructure.EventHandler;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class ExternalTemperatureEventsHandler  extends EventHandler<ExternalTemperatureReaded> {
    ThermostateStatus status;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handle(ExternalTemperatureReaded event) {
        status.setExternalTemperature(new Temperature(event.temp));
    }
}
