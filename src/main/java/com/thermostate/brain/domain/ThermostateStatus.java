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

@Service
@Getter
@RequiredArgsConstructor
public class ThermostateStatus {
    private ManuallyEstablishedTemperature manualTemperature = new ManuallyEstablishedTemperature(1600);
    private Temperature currentTemperature = new Temperature(1700);
    private Temperature currentTargetTemperature = new Temperature(1700);
    @Setter
    private Temperature externalTemperature = new Temperature(1600);
    private ActiveSchedules activeSchedules = new ActiveSchedules();
    private Boolean active = false;
    private final EventBus eventBus;
    private ActiveTemperature activeTemperature = manualTemperature;


    public void setManualTemperature(Temperature manualTemperature) {
        this.manualTemperature.setTemperature(manualTemperature);
        this.activeTemperature = this.manualTemperature;
        updateStatus();
    }

    public void setCurrentTemperature(Temperature currentTemperature) {
        this.currentTemperature = currentTemperature;
        updateStatus();
    }

    public void updateStatus() {
        var previous = active;
        active = activeTemperature.higherThan(currentTemperature);
        if (previous != active) {
            eventBus.emit(ThermostateSwitched.of(active));
        }
    }

    public void activate(ThermostatAdapter adapter) {
        adapter.setActiveStatus(active);
    }

    public void makeAwareOfSchedules(List<Schedule> schedules, DateHelper dateHelper) {
        boolean wasEmpty = schedules.isEmpty();
        schedules.forEach(it -> makeAwareOfSchedule(it, dateHelper));
        if (wasEmpty != schedules.isEmpty()) {
            activeTemperature = this.activeSchedules;
        }
    }

    public void makeAwareOfSchedule(Schedule schedule, DateHelper dateHelper) {
        activeSchedules.makeAwareOf(schedule, dateHelper);
    }
}
