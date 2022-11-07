package com.thermostate.schedules.application;

import com.thermostate.schedules.model.ScheduleRepo;
import org.springframework.stereotype.Component;

@Component
public class DeleteSchedule {
    final ScheduleRepo scheduleRepo;

    public DeleteSchedule(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public void execute(Integer id) {
        scheduleRepo.deleteById(id);
    }
}
