package com.thermostate.schedules.model;

import com.thermostate.shared.ClientError;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@EqualsAndHashCode
public class Schedule {
    public final Integer id;
    public final String weekDays;
    public final String timeFrom;
    public final String timeTo;
    public final Boolean active;
    public final Integer minTemp;
    public final LocalDate createdAt;

    public Schedule(Integer id, String weekDays, String timeFrom, String timeTo, Boolean active, Integer minTemp, LocalDate createdAt) {
        this.id = id;
        this.weekDays = weekDays;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.active = active;
        this.minTemp = minTemp;
        this.createdAt = createdAt;
        checkData();
    }

    private void checkData() {
        if (!(isValidWeekDays(weekDays)
                && isValidTime(timeFrom)
                && isValidTime(timeTo)
                && isValidActive(active)
                && isValidMinTemp(minTemp))){
            throw ClientError.becauseInvalidDataFromClient();
        }
    }

    public boolean isValidWeekDays(String weekDays) {
        return Stream.of(weekDays.split(","))
                .filter(c -> !"LMXJVSD".contains(c) || c.length() != 1)
                .findAny()
                .isEmpty();
    }

    public boolean isValidDateTo(LocalDate dateTo) {
        return dateTo != null;
    }

    public boolean isValidTime(String timeFrom) {
        if (timeFrom == null) {
            return false;
        }
        Pattern timePattern = Pattern.compile("[0-2][0-9]:[0-5][0-9]");
        Matcher mat = timePattern.matcher(timeFrom);
        return isNotEmpty(timeFrom) || mat.matches();
    }

    public boolean isValidActive(Boolean active) {
        return active != null;
    }

    public boolean isValidMinTemp(Integer minTemp) {
        return minTemp != null && minTemp > 0;
    }
}
