package com.thermostate.schedules.application;

import com.thermostate.schedules.domain.Schedule;
import com.thermostate.schedules.domain.ScheduleRepo;
import com.thermostate.schedules.domain.events.EventBus;
import org.springframework.stereotype.Component;

@Component
public class DeleteSchedule {
    final ScheduleRepo scheduleRepo;
    final EventBus eventBus;

    public DeleteSchedule(ScheduleRepo scheduleRepo, EventBus eventBus) {
        this.scheduleRepo = scheduleRepo;
        this.eventBus = eventBus;
    }

    public void execute(Schedule schedule) {
        schedule.delete(scheduleRepo);
        schedule.publishEventsIn(eventBus);
    }
}
