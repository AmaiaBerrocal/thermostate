package com.thermostate.schedules.application;

import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.schedules.model.Schedule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import java.util.Date;

@Component
@AllArgsConstructor
public class ScheduleChecker {
    private ThermostateStatus status;
    public static String[] DAYS = {"D","L","M","X","J","V","S"};
    public static String nowAsDayOfWeek(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return DAYS[c.get(Calendar.DAY_OF_WEEK)-1];
    }

    public void execute(List<Schedule> schedules) {
        var timeNow = nowAsNumber();
        schedules.stream()
                .filter(Schedule::isActive)
                .filter(schedule -> schedule.getWeekDays().contains(nowAsDayOfWeek()))
                .filter(schedule ->
                            hourOfDayAsNumber(schedule.getTimeFrom()) >= timeNow &&
                            hourOfDayAsNumber(schedule.getTimeTo()) < timeNow
                )
                .findFirst()
                .ifPresent(schedule -> status.setActiveSchedule(schedule));
    }

    public static int nowAsNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return hourOfDayAsNumber(sdf.format(new Date()));
    }


    public static int hourOfDayAsNumber(String hour) {
        int h = Integer.parseInt(hour.substring(0,2))*100;
        int m = Integer.parseInt(hour.substring(3));
        return h+m;
    }
}
