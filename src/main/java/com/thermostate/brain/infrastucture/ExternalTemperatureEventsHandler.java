package com.thermostate.brain.infrastucture;

import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.externaltemperature.model.events.ExternalTemperatureReaded;
import com.thermostate.shared.domain.Temperature;
import com.thermostate.shared.events.infrastructure.EventHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ExternalTemperatureEventsHandler implements EventHandler<ExternalTemperatureReaded> {
    ThermostateStatus status;
    @Override
    public void handle(ExternalTemperatureReaded event) {
        status.setExternalTemperature(new Temperature(event.temp));
    }
}
