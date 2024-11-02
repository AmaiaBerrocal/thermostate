package com.thermostate.schedules.application;

import com.thermostate.schedules.domain.Schedule;
import com.thermostate.schedules.domain.ScheduleRepo;
import com.thermostate.schedules.domain.events.EventBus;
import org.springframework.stereotype.Component;

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
