package com.thermostate.temperature.infrastructure;

import com.thermostate.temperature.model.event.TargetTemperatureChanged;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DesiredTemperatureChangedListener {
  @EventListener
  public void lcdTemperatureChanged(TargetTemperatureChanged temperature) {
    System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
  }
}
