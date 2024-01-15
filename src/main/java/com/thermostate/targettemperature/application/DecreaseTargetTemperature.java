package com.thermostate.targettemperature.application;

import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.schedules.model.events.EventBus;
import com.thermostate.targettemperature.model.TemperatureChange;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DecreaseTargetTemperature {

    final EventBus eventBus;

    public void execute(TemperatureChange temp) {
        temp.publishEventsIn(eventBus);
    }
}
