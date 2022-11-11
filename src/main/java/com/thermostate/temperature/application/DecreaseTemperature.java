package com.thermostate.temperature.application;

import com.thermostate.temperature.model.Temperature;
import com.thermostate.temperature.model.TemperatureRepo;
import org.springframework.stereotype.Component;

@Component
public class DecreaseTemperature {
    final TemperatureRepo temperatureRepo;

    public DecreaseTemperature(TemperatureRepo temperatureRepo) {
        this.temperatureRepo = temperatureRepo;
    }

    public void execute(Integer temp) {
        Integer decrementTemp = temp - 100;
        Temperature temperature = new Temperature(decrementTemp);
        temperatureRepo.updateTemp(temperature);
    }
}
