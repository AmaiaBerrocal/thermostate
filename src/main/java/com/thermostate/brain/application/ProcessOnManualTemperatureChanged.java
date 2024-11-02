package com.thermostate.brain.application;

import com.thermostate.brain.domain.ThermostatAdapter;
import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.shared.domain.Temperature;
import com.thermostate.shared.events.infrastructure.EventHandler;
import com.thermostate.targettemperature.domain.event.ManualTemperatureChanged;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProcessOnManualTemperatureChanged extends EventHandler<ManualTemperatureChanged> {
    private final ThermostateStatus status;
    private final ThermostatAdapter adapter;

    @Override
    public void handle(ManualTemperatureChanged event) {
        status.setTargetTemperature(Temperature.of(event.amount + status.getTargetTemperature().getTemp()));
        status.activate(adapter);}
}
