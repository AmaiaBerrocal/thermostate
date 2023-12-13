package com.thermostate.desiredtemperature.application;

import com.thermostate.desiredtemperature.model.Temperature;
import com.thermostate.desiredtemperature.model.TemperatureRepo;
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
