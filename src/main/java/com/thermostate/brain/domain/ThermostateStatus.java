package com.thermostate.brain.domain;

import com.thermostate.brain.domain.events.ThermostateSwitched;
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
    private Temperature roomTemperature = new Temperature(1700);
    private Temperature targetTemperature = new Temperature(1700);
    @Setter
    private Temperature externalTemperature = new Temperature(1600);
    private Boolean active = false;
    private final EventBus eventBus;


    public void setTargetTemperature(Temperature targetTemperature) {
        this.targetTemperature = targetTemperature;
        updateStatus();
    }

    public void setRoomTemperature(Temperature roomTemperature) {
        this.roomTemperature = roomTemperature;
        updateStatus();
    }

    public void updateStatus() {
        active = targetTemperature.higherThan(roomTemperature);
        eventBus.emit(ThermostateSwitched.of(active));
    }

    public void activate(ThermostatAdapter adapter) {
        adapter.setActiveStatus(active);
    }

    public void makeAwareOfSchedules(List<Schedule> schedules) {
        schedules.stream().filter(Schedule::isActive).findFirst().ifPresent(it -> {
            setTargetTemperature(it.getMinTemperature());
        });
    }

}
