package com.thermostate.temperature.application;

import com.thermostate.temperature.model.Temperature;
import com.thermostate.temperature.model.TemperatureRepo;
import org.springframework.stereotype.Component;

@Component
public class IncreaseTemperature {
    final TemperatureRepo temperatureRepo;

    public IncreaseTemperature(TemperatureRepo temperatureRepo) {
        this.temperatureRepo = temperatureRepo;
    }

    public void execute(Integer temp) {
        Integer incrementTemp = temp + 100;
        Temperature temperature = new Temperature(incrementTemp);
        temperatureRepo.updateTemp(temperature);
    }
}
