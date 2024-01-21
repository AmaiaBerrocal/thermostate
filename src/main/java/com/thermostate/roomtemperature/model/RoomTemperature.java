package com.thermostate.roomtemperature.model;

import com.thermostate.roomtemperature.model.events.RoomTemperatureReaded;
import com.thermostate.shared.events.domain.AggregateRoot;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
