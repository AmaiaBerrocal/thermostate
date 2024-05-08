package com.thermostate.stadistics.application;

import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.shared.domain.Temperature;
import com.thermostate.roomtemperature.model.events.RoomTemperatureRead;
import com.thermostate.shared.events.infrastructure.EventHandler;
import com.thermostate.stadistics.domain.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SaveDataOnRoomTemperatureRead implements EventHandler<RoomTemperatureRead> {

    @Override
    public void handle(RoomTemperatureRead event) {
        State state = State.from(event);
    }
}
