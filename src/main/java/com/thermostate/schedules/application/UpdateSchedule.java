package com.thermostate.schedules.application;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleRepo;
import com.thermostate.schedules.model.events.EventBus;
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

    public void execute(Schedule schedule) {
        schedule.update(scheduleRepo);
        schedule.publishEventsIn(eventBus);
    }
}
