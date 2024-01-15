package com.thermostate.schedules.infrastructure.data;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class ScheduleJpaRepo implements ScheduleRepo {
    final Schedules schedules;

    @Override
    public void create(Schedule schedule) {
        schedules.save(ScheduleJpa.fromDomain(schedule));
    }

    @Override
    public Schedule getById(Integer id) {
        return schedules.findById(id).map(ScheduleJpa::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("No schedule found"));
    }

    @Override
    public void update(Schedule schedule) {
        schedules.save(ScheduleJpa.fromDomain(schedule));
    }

    @Override
    public List<Schedule> getAll() {
        return schedules.findAll().stream().map(ScheduleJpa::toDomain).toList();
    }

    @Override
    public void deleteById(Integer id) {
        schedules.deleteById(id);
    }
}