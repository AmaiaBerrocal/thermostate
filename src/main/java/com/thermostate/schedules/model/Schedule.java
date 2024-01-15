package com.thermostate.schedules.model;

import com.thermostate.schedules.model.events.ScheduleCreated;
import com.thermostate.schedules.model.events.ScheduleDeleted;
import com.thermostate.schedules.model.events.ScheduleUpdated;
import com.thermostate.shared.ClientError;
import com.thermostate.shared.events.domain.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@EqualsAndHashCode(callSuper = false)
@Getter
public class Schedule extends AggregateRoot {
    public final Integer id;
    public String weekDays;
    public String timeFrom;
    public String timeTo;
    public Boolean active;
    public Integer minTemp;
    public LocalDate createdAt;

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

    public Schedule(Integer id) {
        this.id = id;
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

    public void createIn(ScheduleRepo scheduleRepo) {
        scheduleRepo.create(this);
        record(new ScheduleCreated(id));
    }

    public void update(ScheduleRepo scheduleRepo) {
        scheduleRepo.update(this);
        record(new ScheduleUpdated(id));
    }

    public void delete(ScheduleRepo scheduleRepo) {
        scheduleRepo.deleteById(id);
        record(new ScheduleDeleted(id));
    }

    public boolean isActive() {
        return active;
    }
}
