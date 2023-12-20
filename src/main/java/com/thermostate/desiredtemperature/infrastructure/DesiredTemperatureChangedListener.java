package com.thermostate.desiredtemperature.infrastructure;

import com.thermostate.desiredtemperature.model.event.TargetTemperatureChanged;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DesiredTemperatureChangedListener {
  @EventListener
  public void lcdTemperatureChanged(TargetTemperatureChanged temperature) {
    System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
  }
}
