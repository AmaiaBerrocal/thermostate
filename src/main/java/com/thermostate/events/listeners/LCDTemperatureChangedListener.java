package com.thermostate.events.listeners;

import com.google.common.eventbus.Subscribe;
import com.thermostate.temperature.model.Temperature;

public class LCDTemperatureChangedListener {
  @Subscribe
  public void lcdTemperatureChanged(Temperature temperature) {
    
  }
}
