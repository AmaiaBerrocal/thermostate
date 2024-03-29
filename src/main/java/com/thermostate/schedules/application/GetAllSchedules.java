package com.thermostate.schedules.application;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllSchedules {
    final ScheduleRepo scheduleRepo;

    public GetAllSchedules(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public List<Schedule> execute() {
        return scheduleRepo.getAll();
    }
}
