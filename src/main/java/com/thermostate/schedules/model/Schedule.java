package com.thermostate.schedules.model;

import java.time.LocalDate;

public record Schedule(Integer id, LocalDate dateFrom, LocalDate dateTo, String timeFrom, String timeTo, Boolean active, Integer minTemp, LocalDate createdAt) { }
