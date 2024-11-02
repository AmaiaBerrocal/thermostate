package com.thermostate.schedules.model;

import com.thermostate.shared.domain.Temperature;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class ScheduleView {
    public final UUID id;
    public final String weekDays;
    public final  String timeFrom;
    public final  Boolean active;
    public final  Integer minTemp;
    public final  LocalDate createdAt;


    public Temperature getMinTemperature() {
        return Temperature.of(minTemp);
    }
}
