package com.thermostate.schedules.infrastructure;

public interface DateHelper {
    String nowAsDayOfWeek();

    int nowAsNumber();

    int hourOfDayAsNumber(String hour);
}
