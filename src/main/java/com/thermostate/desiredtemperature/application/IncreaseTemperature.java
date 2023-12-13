package com.thermostate.desiredtemperature.application;

import com.thermostate.schedules.model.events.EventBus;
import com.thermostate.desiredtemperature.model.Temperature;
import com.thermostate.desiredtemperature.model.TemperatureChange;
import com.thermostate.desiredtemperature.model.TemperatureRepo;
import org.springframework.stereotype.Component;

@Component
public class IncreaseTemperature {
    final TemperatureRepo temperatureRepo;
    final EventBus eventBus;

    public IncreaseTemperature(TemperatureRepo temperatureRepo, EventBus eventBus) {
        this.temperatureRepo = temperatureRepo;
        this.eventBus = eventBus;
    }

    public void execute(TemperatureChange temp) {
        Temperature temperature = temperatureRepo.getTemp();
        temperature.change(temp, temperatureRepo);
        temperature.publishEventsIn(eventBus);
    }
}
