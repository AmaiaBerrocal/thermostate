package com.thermostate.schedules.application;

import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.schedules.infrastructure.DateHelper;
import com.thermostate.schedules.model.DateCalculator;
import com.thermostate.schedules.model.Schedule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ScheduleChecker {
    private ThermostateStatus status;
    private DateHelper dateHelper;

    public void execute(List<Schedule> schedules) {
        var timeNow = dateHelper.nowAsNumber();
        status.makeAwareOfSchedules(schedules);
                /*.filter(Schedule::isActive)
                .filter(this::isActiveAtThisWeekDay)
                .filter(schedule -> isActiveAtThisTime(schedule, timeNow))
                .findFirst()
                .ifPresent(schedule -> status.setActiveSchedule(schedule));*/
    }

    private boolean isActiveAtThisWeekDay(Schedule schedule) {
        return schedule.getWeekDays().contains(dateHelper.nowAsDayOfWeek());
    }

    private boolean isActiveAtThisTime(Schedule schedule, int timeNow) {
        return dateHelper.hourOfDayAsNumber(schedule.getTimeFrom()) < timeNow &&
                dateHelper.hourOfDayAsNumber(schedule.getTimeTo()) >= timeNow;
    }


}
