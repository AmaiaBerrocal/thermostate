package com.thermostate.externaltemperture.application;

import com.thermostate.externaltemperture.model.ExternalTemperatureRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetExternalTemperature {

  ExternalTemperatureRepo repo;
  public void execute() {
    repo.obtainExternalTemperature();
  }
}
