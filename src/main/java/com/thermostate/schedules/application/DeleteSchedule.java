package com.thermostate.schedules.application;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleRepo;
import com.thermostate.schedules.model.events.EventBus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteSchedule {
    final ScheduleRepo scheduleRepo;
    final EventBus eventBus;

    public DeleteSchedule(ScheduleRepo scheduleRepo, EventBus eventBus) {
        this.scheduleRepo = scheduleRepo;
        this.eventBus = eventBus;
    }

    public void execute(UUID id) {
        Schedule schedule = new Schedule(id);
        schedule.delete(scheduleRepo);
        schedule.publishEventsIn(eventBus);
    }
}
