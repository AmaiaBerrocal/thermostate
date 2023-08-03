package com.thermostate.roomtemperature.infrastructure;

import com.google.common.eventbus.Subscribe;
import com.thermostate.temperature.model.Temperature;

public class RoomTemperatureReadedListener {
  @Subscribe
  public void roomTemperatureChanges(Temperature temperature) {

  }
}
