package com.thermostate.brain.application;

import com.thermostate.brain.domain.ThermostatAdapter;
import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.roomtemperature.domain.events.RoomTemperatureRead;
import com.thermostate.shared.domain.Temperature;
import com.thermostate.shared.events.infrastructure.EventHandler;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class RoomTemperatureEventsHandler extends EventHandler<RoomTemperatureRead>  {
    private final ThermostateStatus status;
    private final ThermostatAdapter adapter;

    @Override
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handle(RoomTemperatureRead event) {
        status.setRoomTemperature(Temperature.of(event.temp));
        status.activate(adapter);
    }
}
