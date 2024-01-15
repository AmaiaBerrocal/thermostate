package com.thermostate.shared;

import com.thermostate.externaltemperature.application.GetExternalTemperature;
import com.thermostate.roomtemperature.application.GetRoomTemperature;
import com.thermostate.schedules.application.GetAllSchedules;
import com.thermostate.schedules.application.ScheduleChecker;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.security.Principal;

@Configuration
@EnableScheduling
@AllArgsConstructor
public class SchedulingConfigurator {
    GetRoomTemperature getRoomTemperature;
    GetExternalTemperature getExternalTemperature;
    GetAllSchedules getAllSchedules;
    ScheduleChecker scheduleChecker;


    @Scheduled(fixedDelay = 5000)
    public void scheduleFixedDelayTask() {
        getRoomTemperature.execute();
        scheduleChecker.execute(getAllSchedules.execute());
    }

    @Scheduled(fixedDelay = 20000)
    public void scheduleFixedDelayExternalTemp() {
        getExternalTemperature.execute();
    }
}


