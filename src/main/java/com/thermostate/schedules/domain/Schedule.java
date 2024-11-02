package com.thermostate.schedules.domain;

import com.thermostate.schedules.domain.events.ScheduleCreated;
import com.thermostate.schedules.domain.events.ScheduleDeleted;
import com.thermostate.schedules.domain.events.ScheduleUpdated;
import com.thermostate.shared.ClientError;
import com.thermostate.shared.domain.exceptions.Unauthorized;
import com.thermostate.shared.events.domain.AggregateRoot;
import com.thermostate.users.infrastucture.data.UserType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@EqualsAndHashCode(callSuper = false)
@Getter
public class Schedule extends AggregateRoot {
    public final UUID id;
    public String weekDays;
    public String timeFrom;
    public Boolean active;
    public Integer minTemp;
    public LocalDate createdAt;

    public Schedule(UUID id, String weekDays, String timeFrom, Boolean active, Integer minTemp, UserType userType) {
        this(id, weekDays, timeFrom, active, minTemp, userType, LocalDate.now());
    }
    public Schedule(UUID id,
                    String weekDays,
                    String timeFrom,
                    Boolean active,
                    Integer minTemp,
                    UserType userType,
                    LocalDate createdAt) {
        checkPermissions(userType);
        this.active = true;
        this.id = id;
        this.weekDays = weekDays;
        this.timeFrom = timeFrom;
        this.active = active;
        this.minTemp = minTemp;
        this.createdAt = createdAt;
        checkData();
    }

    private static void checkPermissions(UserType userType) {
        if (!List.of(UserType.THERMOSTAT_USER, UserType.ADMIN).contains(userType)) {
            throw Unauthorized.becauseNotAbleToManageSchedules(userType);
        }
    }

    public Schedule(UUID id) {
        this.id = id;
    }

    private void checkData() {
        if (!(isValidWeekDays(weekDays)
                && isValidTime(timeFrom)
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
        return active && isActiveAtThisWeekDay() && isActiveAtThisTime();
    }

    private boolean isActiveAtThisWeekDay() {
        return weekDays.contains(DateCalculator.nowAsDayOfWeek());
    }

    private boolean isActiveAtThisTime() {
        int timeNow = DateCalculator.nowAsNumber();
        return DateCalculator.timeOfDayAsNumber(timeFrom) == timeNow;
    }
}
