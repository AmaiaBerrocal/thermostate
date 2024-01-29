package com.thermostate.externaltemperature.application;

import com.thermostate.externaltemperature.model.ExternalTemperature;
import com.thermostate.externaltemperature.model.ExternalTemperatureRepo;
import com.thermostate.schedules.model.events.EventBus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetExternalTemperature {

  ExternalTemperatureRepo repo;
  EventBus eventBus;
  public ExternalTemperature execute() {
    var externalTemperature = repo.obtainExternalTemperature();
    externalTemperature.publishEventsIn(eventBus);
    return externalTemperature;
  }
}
