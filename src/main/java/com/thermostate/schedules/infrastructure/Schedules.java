package com.thermostate.schedules.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Schedules extends JpaRepository<ScheduleJpa, Integer> {
}
