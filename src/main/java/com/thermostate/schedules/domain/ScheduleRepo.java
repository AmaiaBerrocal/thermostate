package com.thermostate.schedules.domain;

import java.util.List;
import java.util.UUID;

public interface ScheduleRepo {
    void create(Schedule schedule);
    Schedule getById(UUID id);
    List<Schedule> getAll();
    void update(Schedule schedule);
    void deleteById(UUID id);
}
