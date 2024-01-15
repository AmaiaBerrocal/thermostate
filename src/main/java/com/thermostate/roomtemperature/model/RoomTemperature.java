package com.thermostate.roomtemperature.model;

import com.thermostate.roomtemperature.model.events.RoomTemperatureReaded;
import com.thermostate.shared.events.domain.AggregateRoot;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoomTemperature extends AggregateRoot {
    public final String temp;

    private RoomTemperature(String temp) {
        this.temp = temp;
        record(new RoomTemperatureReaded((int)(Double.valueOf(this.temp)*100)));
    }

    public static RoomTemperature create(String temp) {
        return new RoomTemperature(temp);
    }
}
