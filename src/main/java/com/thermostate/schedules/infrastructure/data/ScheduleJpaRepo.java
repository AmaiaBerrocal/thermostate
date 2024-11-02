package com.thermostate.schedules.infrastructure.data;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleView;
import com.thermostate.schedules.model.ScheduleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class ScheduleJpaRepo implements ScheduleRepo {
    final Schedules schedules;

    @Override
    public void create(Schedule schedule) {
        schedules.save(ScheduleJpa.fromDomain(schedule));
    }

    @Override
    public ScheduleView getById(UUID id) {
        return schedules.findById(id).map(ScheduleJpa::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("No schedule found"));
    }

    @Override
    public void update(Schedule schedule) {
        schedules.save(ScheduleJpa.fromDomain(schedule));
    }

    @Override
    public List<ScheduleView> getAll() {
        return schedules.findAll().stream().map(ScheduleJpa::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        schedules.deleteById(id);
    }
}
