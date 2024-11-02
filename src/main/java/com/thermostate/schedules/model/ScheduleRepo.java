package com.thermostate.schedules.model;

import java.util.List;
import java.util.UUID;

public interface ScheduleRepo {
    void create(Schedule schedule);
    ScheduleView getById(UUID id);
    List<ScheduleView> getAll();
    void update(Schedule schedule);
    void deleteById(UUID id);
}
