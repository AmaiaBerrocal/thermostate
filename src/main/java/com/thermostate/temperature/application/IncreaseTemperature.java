package com.thermostate.temperature.application;

import com.thermostate.shared.events.SpringApplicationEventBus;
import com.thermostate.temperature.model.Temperature;
import com.thermostate.temperature.model.TemperatureChange;
import com.thermostate.temperature.model.TemperatureRepo;
import org.springframework.stereotype.Component;

@Component
public class IncreaseTemperature {
    final TemperatureRepo temperatureRepo;
    final SpringApplicationEventBus eventBus;

    public IncreaseTemperature(TemperatureRepo temperatureRepo, SpringApplicationEventBus eventBus) {
        this.temperatureRepo = temperatureRepo;
        this.eventBus = eventBus;
    }

    public void execute(TemperatureChange temp) {
        Temperature temperature = temperatureRepo.getTemp();
        temperature.change(temp, temperatureRepo);
        temperature.publishEventsIn(eventBus);
    }
}
