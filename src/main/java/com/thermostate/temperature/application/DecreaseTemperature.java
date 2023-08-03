package com.thermostate.temperature.application;

import com.google.common.eventbus.EventBus;
import com.thermostate.temperature.model.Temperature;
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

    public void execute(Integer temp) {
        Integer decrementTemp = temperatureRepo.getTemp().temp() - temp;
        Temperature temperature = new Temperature(decrementTemp);
        temperatureRepo.updateTemp(temperature);
        eventBus.post(temperature);
    }
}
