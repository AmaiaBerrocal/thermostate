package com.thermostate.schedules.application;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleRepo;
import com.thermostate.schedules.model.events.EventBus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class CreateSchedule {

    final ScheduleRepo scheduleRepo;
    final EventBus eventBus;

    public CreateSchedule(ScheduleRepo scheduleRepo, EventBus eventBus) {

        this.scheduleRepo = scheduleRepo;
        this.eventBus = eventBus;
    }

    public void execute(String weekDays,
                        String timeFrom,
                        String timeTo,
                        Boolean active,
                        Integer minTemp) {
        Schedule schedule = new Schedule(null,weekDays, timeFrom, timeTo, active, minTemp, LocalDate.now());
        schedule.createIn(scheduleRepo);
        schedule.publishEventsIn(eventBus);
    }
}
