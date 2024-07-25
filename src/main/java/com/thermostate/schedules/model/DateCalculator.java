package com.thermostate.schedules.model;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateCalculator {
    public static String[] DAYS = {"D","L","M","X","J","V","S"};

    public static String nowAsDayOfWeek() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return DAYS[c.get(Calendar.DAY_OF_WEEK)-1];
    }

    public static int nowAsNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return timeOfDayAsNumber(sdf.format(new Date()));
    }

    public static int timeOfDayAsNumber(String hour) {
        int h = Integer.parseInt(hour.substring(0,2))*100;
        int m = Integer.parseInt(hour.substring(3));
        return h+m;
    }
}
