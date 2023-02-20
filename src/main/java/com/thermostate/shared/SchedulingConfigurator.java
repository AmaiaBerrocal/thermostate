package com.thermostate.shared;

import com.thermostate.roomtemperature.application.GetRoomTemperature;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulingConfigurator {
    GetRoomTemperature getRoomTemperature;

    public SchedulingConfigurator(GetRoomTemperature getRoomTemperature) {
        this.getRoomTemperature = getRoomTemperature;
    }

    @Scheduled(fixedDelay = 5000)
    public void scheduleFixedDelayTask() {
        getRoomTemperature.execute();
    }
}
