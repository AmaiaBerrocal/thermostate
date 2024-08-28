package com.thermostate.targettemperature.application;

import com.thermostate.schedules.domain.events.EventBus;
import com.thermostate.targettemperature.domain.TemperatureChange;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class IncreaseTargetTemperature {
    final EventBus eventBus;

    public void execute(TemperatureChange temp) {
        temp.publishEventsIn(eventBus);
    }
}
