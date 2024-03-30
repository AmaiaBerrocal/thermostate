package com.thermostate.roomtemperature.model;

import com.thermostate.shared.events.domain.RoomTemperatureReaded;
import com.thermostate.shared.events.domain.base.AggregateRoot;

public class RoomTemperature extends AggregateRoot {
    public final Integer temp;

    private RoomTemperature(Integer temp) {
        this.temp = temp;
        record(new RoomTemperatureReaded(this.temp));
    }

    public static RoomTemperature create(Integer temp) {
        return new RoomTemperature(temp);
    }
}
