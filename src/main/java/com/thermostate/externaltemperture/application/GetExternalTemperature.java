package com.thermostate.externaltemperture.application;

import com.thermostate.externaltemperture.model.ExternalTemperature;
import com.thermostate.externaltemperture.model.ExternalTemperatureRepo;
import com.thermostate.shared.events.EventBus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetExternalTemperature {

  ExternalTemperatureRepo repo;
  EventBus eventBus;
  public void execute() {
    ExternalTemperature externalTemperature = repo.obtainExternalTemperature();
    externalTemperature.publishEventsIn(eventBus);
  }
}
