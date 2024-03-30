package com.thermostate.roomtemperature.application;

import com.thermostate.roomtemperature.model.RoomTemperature;
import com.thermostate.roomtemperature.model.RoomTemperatureRepo;
import com.thermostate.shared.events.domain.base.EventBus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetRoomTemperature {
  final RoomTemperatureRepo temperatureRepo;
  final EventBus eventBus;

  public RoomTemperature execute() {
    RoomTemperature temperature = temperatureRepo.getTemp();
    temperature.publishEventsIn(eventBus);
    return temperature;
  }
}
