package com.thermostate.temperature.application;

import com.thermostate.temperature.model.Temperature;
import com.thermostate.temperature.model.TemperatureRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetTemperature {
  final TemperatureRepo temperatureRepo;

  public Temperature execute() {
    return temperatureRepo.getTemp();
  }
}
