package com.thermostate.shared;

import com.thermostate.externaltemperature.application.GetExternalTemperature;
import com.thermostate.roomtemperature.application.GetRoomTemperature;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulingConfigurator {
    GetRoomTemperature getRoomTemperature;
    GetExternalTemperature getExternalTemperature;

    public SchedulingConfigurator(GetRoomTemperature getRoomTemperature, GetExternalTemperature getExternalTemperature) {
        this.getRoomTemperature = getRoomTemperature;
        this.getExternalTemperature = getExternalTemperature;
    }

    @Scheduled(fixedDelay = 5000)
    public void scheduleFixedDelayTask() {
        getRoomTemperature.execute();
    }

    @Scheduled(fixedDelay = 20000)
    public void scheduleFixedDelayExternalTemp() {
        getExternalTemperature.execute();
    }
}
