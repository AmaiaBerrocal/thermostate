package com.thermostate.schedules.application;

import com.thermostate.schedules.domain.ScheduleView;
import com.thermostate.schedules.domain.ScheduleRepo;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetScheduleById {
    final ScheduleRepo scheduleRepo;

    public GetScheduleById(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public ScheduleView execute(UUID id) {
        return scheduleRepo.getById(id);
    }
}
