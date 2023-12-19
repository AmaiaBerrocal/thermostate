package com.thermostate.schedules.application;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleRepo;
import com.thermostate.schedules.model.events.EventBus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UpdateSchedule {
    final ScheduleRepo scheduleRepo;
    final EventBus eventBus;

    public UpdateSchedule(ScheduleRepo scheduleRepo, EventBus eventBus) {
        this.scheduleRepo = scheduleRepo;
        this.eventBus = eventBus;
    }

    public void execute(Integer id, String weekDays, String timeFrom, String timeTo, Boolean active, Integer minTemp) {
        Schedule schedule = new Schedule(id, weekDays, timeFrom, timeTo, active, minTemp, LocalDate.now());
        schedule.update(scheduleRepo);
        schedule.publishEventsIn(eventBus);
    }
}
