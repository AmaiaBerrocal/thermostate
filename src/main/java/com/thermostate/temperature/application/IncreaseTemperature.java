package com.thermostate.temperature.application;

import com.google.common.eventbus.EventBus;
import com.thermostate.temperature.model.Temperature;
import com.thermostate.temperature.model.TemperatureRepo;
import org.springframework.stereotype.Component;

@Component
public class IncreaseTemperature {
    final TemperatureRepo temperatureRepo;
    final EventBus eventBus;

    public IncreaseTemperature(TemperatureRepo temperatureRepo, EventBus eventBus) {
        this.temperatureRepo = temperatureRepo;
        this.eventBus = eventBus;
    }

    public void execute(Integer temp) {
        Integer incrementTemp = temperatureRepo.getTemp().temp() + temp;
        Temperature temperature = new Temperature(incrementTemp);
        temperatureRepo.updateTemp(temperature);
        eventBus.post(temperature);
    }
}
