package com.thermostate.temperature.infrastructure;

import com.google.common.eventbus.Subscribe;
import com.thermostate.temperature.model.Temperature;

public class DesiredTemperatureChangedListener {
  @Subscribe
  public void lcdTemperatureChanged(Temperature temperature) {
    System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
  }
}
