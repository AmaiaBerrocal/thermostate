package com.thermostate.brain.infrastucture;

import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.shared.domain.Temperature;
import com.thermostate.targettemperature.model.event.TargetTemperatureChanged;
import com.thermostate.shared.events.infrastructure.EventHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TargetTemperatureEventsHandler implements EventHandler<TargetTemperatureChanged> {
    ThermostateStatus status;
    @Override
    public void handle(TargetTemperatureChanged event) {
        System.out.println("TemperatureEvent reach: " + event.eventName() + " " + status.getTargetTemperature().getTemp() + " + " + event.amount);
        status.setTargetTemperature(new Temperature(status.getTargetTemperature().getTemp() + event.amount));
    }
}
