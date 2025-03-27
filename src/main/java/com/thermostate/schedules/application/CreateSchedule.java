package com.thermostate.schedules.application;

import com.thermostate.schedules.domain.Schedule;
import com.thermostate.schedules.domain.ScheduleRepo;
import com.thermostate.schedules.domain.events.EventBus;
import org.springframework.stereotype.Component;


@Component
public class CreateSchedule {

    final ScheduleRepo scheduleRepo;
    final EventBus eventBus;

    public CreateSchedule(ScheduleRepo scheduleRepo, EventBus eventBus) {

        this.scheduleRepo = scheduleRepo;
        this.eventBus = eventBus;
    }

    public void execute(Schedule schedule) {
        schedule.createIn(scheduleRepo);
        schedule.publishEventsIn(eventBus);
    }
}
