package com.thermostate.roomtemperature.model;

import com.thermostate.shared.events.AggregateRoot;

public class RoomTemperature extends AggregateRoot {
    public final String temp;

    private RoomTemperature(String temp) {
        this.temp = temp;
    }

    public static RoomTemperature create(String temp) {
        return new RoomTemperature(temp);
    }
}
