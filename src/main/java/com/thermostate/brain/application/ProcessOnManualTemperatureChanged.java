package com.thermostate.brain.application;

import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.shared.domain.Temperature;
import com.thermostate.shared.events.infrastructure.EventHandler;
import com.thermostate.targettemperature.model.event.ManualTemperatureChanged;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProcessOnManualTemperatureChanged extends EventHandler<ManualTemperatureChanged> {
    ThermostateStatus status;

    @Override
    public void handle(ManualTemperatureChanged event) {
        status.setManualTemperature(new Temperature(status.getCurrentTargetTemperature().getTemp() + event.amount));
    }
}
