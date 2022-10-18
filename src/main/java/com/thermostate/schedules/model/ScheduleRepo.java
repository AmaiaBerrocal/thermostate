package com.thermostate.schedules.model;

import java.util.List;
import java.util.Map;

public interface ScheduleRepo {
    void create(Schedule schedule);
    Schedule getById(Integer id);
    List<Schedule> getAll();
    //update
    //delete
}
