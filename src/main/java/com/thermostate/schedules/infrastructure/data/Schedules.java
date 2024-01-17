package com.thermostate.schedules.infrastructure.data;

import com.thermostate.schedules.infrastructure.data.ScheduleJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface Schedules extends JpaRepository<ScheduleJpa, UUID> {
}
