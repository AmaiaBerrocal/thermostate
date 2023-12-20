package com.thermostate.desiredtemperature.infrastructure.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DesiredTemperatures  extends JpaRepository<TemperatureJpa, Integer> {
}
