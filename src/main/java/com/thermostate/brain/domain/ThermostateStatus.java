package com.thermostate.brain.domain;

import com.thermostate.brain.domain.events.ThermostateSwitched;
import com.thermostate.schedules.infrastructure.DateHelper;
import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.events.EventBus;
import com.thermostate.shared.domain.Temperature;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Getter
@RequiredArgsConstructor
public class ThermostateStatus {
    private Temperature targetTemperature = new Temperature(1600);
    private Temperature currentTemperature = new Temperature(1700);
    @Setter
    private Temperature externalTemperature = new Temperature(1600);
    private ActiveSchedules activeSchedules;
    private Boolean active = false;
    private final EventBus eventBus;
    private DateHelper dateHelper;


    public void setTargetTemperature(Temperature targetTemperature) {
        this.targetTemperature = targetTemperature;
        updateStatus();
    }

    public void setCurrentTemperature(Temperature currentTemperature) {
        this.currentTemperature = currentTemperature;
        updateStatus();
    }

    public void updateStatus() {
        var previous = active;
        active = targetTemperature.getTemp() >= currentTemperature.getTemp();
        if (previous != null) {
            eventBus.emit(ThermostateSwitched.of(active));
        }
    }

    public void activate(ThermostatAdapter adapter) {
        adapter.setActiveStatus(active);
    }

    public void makeAwareOfSchedules(List<Schedule> schedules) {
        schedules.forEach(this::makeAwareOfSchedule);
    }

    public void makeAwareOfSchedule(Schedule schedule) {
        activeSchedules.makeAwareOf(schedule, dateHelper);

    }
}
