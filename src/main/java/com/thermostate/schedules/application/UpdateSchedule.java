package com.thermostate.schedules.application;

import com.thermostate.schedules.domain.Schedule;
import com.thermostate.schedules.domain.ScheduleRepo;
import com.thermostate.schedules.domain.events.EventBus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class UpdateSchedule {
    final ScheduleRepo scheduleRepo;
    final EventBus eventBus;

    public UpdateSchedule(ScheduleRepo scheduleRepo, EventBus eventBus) {
        this.scheduleRepo = scheduleRepo;
        this.eventBus = eventBus;
    }

    public void execute(UUID id, String weekDays, String timeFrom, Boolean active, Integer minTemp) {
        Schedule schedule = new Schedule(id, weekDays, timeFrom, active, minTemp, LocalDate.now());
        schedule.update(scheduleRepo);
        schedule.publishEventsIn(eventBus);
    }
}
