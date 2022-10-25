package com.thermostate.schedules.application;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleRepo;
import org.springframework.stereotype.Component;

@Component
public class GetScheduleById {
    final ScheduleRepo scheduleRepo;

    public GetScheduleById(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public Schedule execute(Integer id) {
        return scheduleRepo.getById(id);
    }
}
