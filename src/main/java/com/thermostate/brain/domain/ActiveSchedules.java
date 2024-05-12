package com.thermostate.brain.domain;

import com.thermostate.schedules.infrastructure.DateHelper;
import com.thermostate.schedules.model.Schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActiveSchedules {
    public static int DEFAULT_TEMP = 1600;
    List<Schedule> scheduleList = new ArrayList<>();
    List<UUID> uuids = new ArrayList<>();

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
    }

    public int getTargetTemperature() {
        if (scheduleList.isEmpty()) {
            return DEFAULT_TEMP;
        }
    }
}
