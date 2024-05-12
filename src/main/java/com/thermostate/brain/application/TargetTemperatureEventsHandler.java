package com.thermostate.brain.application;

import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.shared.domain.Temperature;
import com.thermostate.shared.events.infrastructure.EventHandler;
import com.thermostate.targettemperature.model.event.TargetTemperatureChanged;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class TargetTemperatureEventsHandler  extends EventHandler<TargetTemperatureChanged> {
    ThermostateStatus status;

    @Override
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handle(TargetTemperatureChanged event) {
        status.setTargetTemperature(new Temperature(status.getTargetTemperature().getTemp() + event.amount));
    }
}
