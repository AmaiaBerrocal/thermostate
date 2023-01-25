package com.thermostate.schedules.model;

import java.time.LocalDate;

public record Schedule(Integer id, String weekDays, String timeFrom, String timeTo, Boolean active, Integer minTemp, LocalDate createdAt) { }
