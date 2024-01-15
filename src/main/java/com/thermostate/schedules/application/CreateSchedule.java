package com.thermostate.schedules.application;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleRepo;
import com.thermostate.schedules.model.events.EventBus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;


@Component
public class CreateSchedule {

    final ScheduleRepo scheduleRepo;
    final EventBus eventBus;

    public CreateSchedule(ScheduleRepo scheduleRepo, EventBus eventBus) {

        this.scheduleRepo = scheduleRepo;
        this.eventBus = eventBus;
    }

    public void execute(UUID id,
                        String weekDays,
                        String timeFrom,
                        String timeTo,
                        Boolean active,
                        Integer minTemp) {
        Schedule schedule = new Schedule(id,weekDays, timeFrom, timeTo, active, minTemp, LocalDate.now());
        schedule.createIn(scheduleRepo);
        schedule.publishEventsIn(eventBus);
    }
}
