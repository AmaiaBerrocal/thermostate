package com.thermostate.brain.application;

import com.thermostate.brain.domain.ThermostatAdapter;
import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.schedules.model.events.EventBus;
import com.thermostate.shared.domain.Temperature;
import com.thermostate.roomtemperature.model.events.RoomTemperatureRead;
import com.thermostate.shared.events.infrastructure.EventHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoomTemperatureEventsHandler implements EventHandler<RoomTemperatureRead> {
    private final ThermostateStatus status;
    private final ThermostatAdapter adapter;
    private final EventBus eventBus;

    @Override
    public void handle(RoomTemperatureRead event) {
        status.setCurrentTemperature(new Temperature(event.temp));
        status.evaluate(adapter);
        status.publishEventsIn(eventBus);
    }
}
