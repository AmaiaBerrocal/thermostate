package com.thermostate.desiredtemperature.infrastructure.data;

import com.thermostate.desiredtemperature.model.Temperature;
import com.thermostate.desiredtemperature.model.TemperatureRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemperatureJpaRepo implements TemperatureRepo {
    private final DesiredTemperatures repository;
    @Override
    public Temperature getTemp() {
        return repository.findById(1).get().toDomain();
    }

    @Override
    public void updateTemp(Temperature temperature) {
        repository.save(new TemperatureJpa(1, temperature.getTemp()));
    }
}
