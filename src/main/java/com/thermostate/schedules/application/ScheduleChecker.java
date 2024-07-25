package com.thermostate.schedules.application;

import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.schedules.model.Schedule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ScheduleChecker {
    private ThermostateStatus status;

    public void execute(List<Schedule> schedules) {
        status.makeAwareOfSchedules(schedules);
    }
}
