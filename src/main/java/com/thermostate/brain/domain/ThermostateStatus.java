package com.thermostate.brain.domain;

import com.thermostate.brain.infrastucture.RaspberryGPIOAdapter;
import com.thermostate.shared.domain.Temperature;
import com.thermostate.schedules.model.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class ThermostateStatus {
    private Temperature targetTemperature = new Temperature(1600);
    private Temperature currentTemperature = new Temperature(1600);
    private Schedule activeSchedule;
    private final ThermostatAdapter adapter;


    public void setTargetTemperature(Temperature targetTemperature) {
        this.targetTemperature = targetTemperature;
        updateStatus();
    }

    public void setCurrentTemperature(Temperature currentTemperature) {
        this.currentTemperature = currentTemperature;
        updateStatus();
    }

    public void setActiveSchedule(Schedule activeSchedule) {
        this.activeSchedule = activeSchedule;
        setTargetTemperature(new Temperature(activeSchedule.getMinTemp()));
        updateStatus();
    }

    public void updateStatus() {
        adapter.setState(targetTemperature.getTemp() >= currentTemperature.getTemp());
    }
}
