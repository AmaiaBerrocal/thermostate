package com.thermostate.schedules.application;

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
public class UpdateSchedule {
    final ScheduleRepo scheduleRepo;

    public UpdateSchedule(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public void execute(Integer id, String weekDays, String timeFrom, String timeTo, Boolean active, Integer minTemp) {
        checkData(weekDays, timeFrom, timeTo, active, minTemp);
        Schedule schedule = new Schedule(id, weekDays, timeFrom, timeTo, active, minTemp, null);
        scheduleRepo.update(schedule);
    }
    private void checkData(String weekDays, String timeFrom, String timeTo, Boolean active, Integer minTemp) {
        if (!(isValidWeekDays(weekDays)
                && isValidTimeFrom(timeFrom)
                && isValidTimeTo(timeTo)
                && isValidActive(active)
                && isValidMinTemp(minTemp))){
            throw ClientError.becauseInvalidDataFromClient();
        }
    }

    public boolean isValidWeekDays(String weekDays) {
        return Stream.of(weekDays.split(","))
            .filter(c -> !"LMXJV".contains(c) || c.length() != 1)
            .findAny()
            .isEmpty();
    }

    public boolean isValidDateTo(LocalDate dateTo) {
        return dateTo != null;
    }

    public boolean isValidTimeFrom(String timeFrom) {
        if (timeFrom == null) {
            return false;
        }
        Pattern timePattern = Pattern.compile("[0-2][0-9]:[0-5][0-9]");
        Matcher mat = timePattern.matcher(timeFrom);
        return isNotEmpty(timeFrom) || mat.matches();
    }

    public boolean isValidTimeTo(String timeTo) {
        if (timeTo == null) {
            return false;
        }
        Pattern timePattern = Pattern.compile("[0-2][0-9]:[0-5][0-9]");
        Matcher mat = timePattern.matcher(timeTo);
        return isNotEmpty(timeTo) && mat.matches();
    }

    public boolean isValidActive(Boolean active) {
        return active != null;
    }

    public boolean isValidMinTemp(Integer minTemp) {
        return minTemp != null && minTemp > 0;
    }
}
