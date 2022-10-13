package com.thermostate.schedules.application;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleRepo;
import com.thermostate.shared.ClientError;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Component
public class CreateSchedule {

    final ScheduleRepo scheduleRepo;

    public CreateSchedule(ScheduleRepo scheduleRepo) {

        this.scheduleRepo = scheduleRepo;
    }

    public void execute(LocalDate dateFrom, LocalDate dateTo, String timeFrom, String timeTo, Boolean active, Integer minTemp) {
        checkData(dateFrom, dateTo, timeFrom, timeTo, active, minTemp);
        Schedule schedule = new Schedule(null,dateFrom, dateTo, timeFrom, timeTo, active, minTemp, null);
        scheduleRepo.create(schedule);
    }

    private void checkData(LocalDate dateFrom, LocalDate dateTo, String timeFrom, String timeTo, Boolean active, Integer minTemp) {
        if (!(isValidDateFrom(dateFrom)
                && isValidDateTo(dateTo)
                && isValidTimeFrom(timeFrom)
                && isValidTimeTo(timeTo)
                && isValidActive(active)
                && isValidMinTemp(minTemp))){
            throw (new ClientError());
        }
    }

    public boolean isValidDateFrom(LocalDate dateFrom) {
        return dateFrom != null;
    }

    public boolean isValidDateTo(LocalDate dateTo) {
        return dateTo != null;
    }

    public boolean isValidTimeFrom(String timeFrom) {
        Pattern timePattern = Pattern.compile("[0-2][0-9]:[0-5][0-9]");
        Matcher mat = timePattern.matcher(timeFrom);
        return isNotEmpty(timeFrom) && mat.matches();
    }

    public boolean isValidTimeTo(String timeTo) {
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
