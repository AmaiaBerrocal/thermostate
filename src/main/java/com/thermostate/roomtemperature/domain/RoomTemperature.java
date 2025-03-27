package com.thermostate.roomtemperature.domain;

import com.thermostate.roomtemperature.domain.events.RoomTemperatureRead;
import com.thermostate.shared.events.domain.AggregateRoot;

public class RoomTemperature extends AggregateRoot {
    public final Integer temp;

    private RoomTemperature(Integer temp) {
        this.temp = temp;
        record(new RoomTemperatureRead(this.temp));
    }

    public static RoomTemperature create(Integer temp) {
        return new RoomTemperature(temp);
    }
}
