package com.thermostate.schedules.model;

import com.thermostate.schedules.infrastructure.DateHelper;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateCalculator implements DateHelper {
    public static String[] DAYS = {"D","L","M","X","J","V","S"};

    public String nowAsDayOfWeek() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return DAYS[c.get(Calendar.DAY_OF_WEEK)-1];
    }

    public int nowAsNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return hourOfDayAsNumber(sdf.format(new Date()));
    }

    public int hourOfDayAsNumber(String hour) {
        int h = Integer.parseInt(hour.substring(0,2))*100;
        int m = Integer.parseInt(hour.substring(3));
        return h+m;
    }
}
