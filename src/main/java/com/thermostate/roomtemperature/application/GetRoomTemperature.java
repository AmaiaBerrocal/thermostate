package com.thermostate.roomtemperature.application;

import com.google.common.eventbus.EventBus;
import com.thermostate.roomtemperature.model.RoomTemperature;
import com.thermostate.roomtemperature.model.RoomTemperatureRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetRoomTemperature {
  final RoomTemperatureRepo temperatureRepo;
  final EventBus eventBus;

  public RoomTemperature execute() {
    RoomTemperature temperature = temperatureRepo.getTemp();
    eventBus.post(temperature);
    return temperature;
  }
}
