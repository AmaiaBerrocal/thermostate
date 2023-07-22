package com.thermostate.schedules.application;

import com.thermostate.schedules.infrastructure.ScheduleDbRepo;
import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleRepo;
import com.thermostate.shared.ClientError;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Component
public class CreateSchedule {

    final ScheduleRepo scheduleRepo;

    public CreateSchedule(ScheduleRepo scheduleRepo) {

        this.scheduleRepo = scheduleRepo;
    }

    public void execute(String weekDays,
                        String timeFrom,
                        String timeTo,
                        Boolean active,
                        Integer minTemp) {
        Schedule schedule = new Schedule(null,weekDays, timeFrom, timeTo, active, minTemp, null);
        scheduleRepo.create(schedule);
    }
}
