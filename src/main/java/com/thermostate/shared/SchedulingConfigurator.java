package com.thermostate.shared;

import com.thermostate.externaltemperature.application.GetExternalTemperature;
import com.thermostate.roomtemperature.application.GetRoomTemperature;
import com.thermostate.schedules.application.GetAllSchedules;
import com.thermostate.schedules.application.ScheduleChecker;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWarDeployment;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.security.Principal;

@Configuration
@EnableScheduling
@AllArgsConstructor
@ConditionalOnExpression("'${spring.profiles.active}' != 'test'")
public class SchedulingConfigurator {
    GetRoomTemperature getRoomTemperature;
    GetExternalTemperature getExternalTemperature;
    GetAllSchedules getAllSchedules;
    ScheduleChecker scheduleChecker;

    @Scheduled(fixedDelay = 60001)
    public void scheduleFixedDelayTask() {
        scheduleChecker.execute(getAllSchedules.execute());
    }

    @Scheduled(fixedDelay = 20000)
    public void scheduleFixedDelayExternalTemp() {
        getExternalTemperature.execute();
        getRoomTemperature.execute();
    }
}


