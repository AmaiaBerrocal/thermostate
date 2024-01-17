package com.thermostate.brain.domain;

import com.thermostate.shared.domain.Temperature;
import com.thermostate.schedules.model.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Getter
@RequiredArgsConstructor
public class ThermostateStatus {
    private Temperature targetTemperature = new Temperature(1600);
    private Temperature currentTemperature = new Temperature(1600);
    @Setter
    private Temperature externalTemperature = new Temperature(1600);
    private Schedule activeSchedule;
    private Boolean active = false;


    public void setTargetTemperature(Temperature targetTemperature) {
        this.targetTemperature = targetTemperature;
        updateStatus();
    }

    public void setCurrentTemperature(Temperature currentTemperature) {
        this.currentTemperature = currentTemperature;
        updateStatus();
    }

    public void setActiveSchedule(Schedule newActiveSchedule) {
        if (Objects.equals(activeSchedule.id, newActiveSchedule.id)) {
            return;
        }
        this.activeSchedule = newActiveSchedule;
        setTargetTemperature(new Temperature(activeSchedule.getMinTemp()));
    }

    public void updateStatus() {
        active = targetTemperature.getTemp() >= currentTemperature.getTemp();
    }
}
