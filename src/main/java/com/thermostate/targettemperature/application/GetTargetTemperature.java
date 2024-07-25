package com.thermostate.targettemperature.application;

import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.shared.domain.Temperature;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetTargetTemperature {
  final ThermostateStatus status;

  public Temperature execute() {
    return Temperature.of(status.getTargetTemperature().getTemp());
  }
}
