package com.thermostate.brain.domain;

import com.thermostate.schedules.infrastructure.DateHelper;
import com.thermostate.schedules.model.Schedule;
import com.thermostate.shared.domain.Temperature;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActiveSchedules implements ActiveTemperature {
    public static int DEFAULT_TEMP = 1600;
    List<Schedule> scheduleList = new ArrayList<>();
    List<UUID> uuids = new ArrayList<>();
    Integer temperature = null;

    public void add(Schedule schedule) {
        if (uuids.contains(schedule.id)) {
            return;
        }
        scheduleList.add(schedule);
        uuids.add(schedule.id);
    }

    public void remove(Schedule schedule) {
        if (!uuids.contains(schedule.id)) {
            return;
        }
        Schedule toErase = scheduleList.stream()
                .filter(activeSchedule -> activeSchedule.id.equals(schedule.id))
                .findFirst().get();
        scheduleList.remove(toErase);
        uuids.remove(toErase.id);
    }

    public void makeAwareOf(Schedule schedule, DateHelper dateHelper) {
        if (schedule.isActive(dateHelper)) {
            add(schedule);
        } else {
            remove(schedule);
        }
        temperature = getScheduledTemperature(dateHelper);
    }

    public int getScheduledTemperature(DateHelper dateHelper) {
        if (scheduleList.isEmpty()) {
            return DEFAULT_TEMP;
        }
        return scheduleList.stream()
                .map(Schedule::getMinTemp)
                .max(Integer::compareTo)
                .orElse(-1);
    }

    @Override
    public Boolean higherThan(Temperature currentTemperature) {
        if (temperature == null) {
            return false;
        }
        return temperature > currentTemperature.getTemp();
    }

    public boolean isEmpty() {
        return scheduleList.isEmpty();
    }
}
