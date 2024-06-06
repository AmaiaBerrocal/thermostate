package com.thermostate.stadistics.application;

import com.thermostate.roomtemperature.model.events.RoomTemperatureRead;
import com.thermostate.stadistics.domain.State;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class SaveDataOnRoomTemperatureRead  {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handle(RoomTemperatureRead event) {
        State state = State.from(event);
    }
}
