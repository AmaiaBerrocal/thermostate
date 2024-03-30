package com.thermostate.brain.infrastucture;

import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.shared.domain.Temperature;
import com.thermostate.shared.events.domain.RoomTemperatureReaded;
import com.thermostate.shared.events.infrastructure.EventHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoomTemperatureEventsHandler implements EventHandler<RoomTemperatureReaded> {
    ThermostateStatus status;
    @Override
    public void handle(RoomTemperatureReaded event) {
        status.setCurrentTemperature(new Temperature(event.temp));
    }
}
