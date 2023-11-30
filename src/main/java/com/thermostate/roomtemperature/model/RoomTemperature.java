package com.thermostate.roomtemperature.model;

import com.thermostate.roomtemperature.model.events.RoomTemperatureReaded;
import com.thermostate.shared.events.domain.AggregateRoot;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoomTemperature extends AggregateRoot {
    public final String temp;
    public static int NUMBER_OF_DECIMALS = 3;

    private RoomTemperature(String temp) {
        this.temp = temp;
        record(new RoomTemperatureReaded(round(Double.parseDouble(temp))));
    }

    public static RoomTemperature create(String temp) {
        return new RoomTemperature(temp);
    }

    private static double round(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(NUMBER_OF_DECIMALS, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
