package com.thermostate.temperature.application;

import com.thermostate.schedules.model.events.EventBus;
import com.thermostate.temperature.model.Temperature;
import com.thermostate.temperature.model.TemperatureChange;
import com.thermostate.temperature.model.TemperatureRepo;
import org.springframework.stereotype.Component;

@Component
public class DecreaseTemperature {
    final TemperatureRepo temperatureRepo;

    final EventBus eventBus;
    public DecreaseTemperature(TemperatureRepo temperatureRepo, EventBus eventBus) {
        this.temperatureRepo = temperatureRepo;
        this.eventBus = eventBus;
    }

    public void execute(TemperatureChange temp) {
        Temperature temperature = temperatureRepo.getTemp();
        temperature.change(temp, temperatureRepo);
        temperature.publishEventsIn(eventBus);
    }
}
