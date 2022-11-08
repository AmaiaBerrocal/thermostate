package com.thermostate.schedules.model;

import java.util.List;

public interface ScheduleRepo {
    void create(Schedule schedule);
    Schedule getById(Integer id);
    List<Schedule> getAll();
    void update(Schedule schedule);
    void deleteById(Integer id);
}
