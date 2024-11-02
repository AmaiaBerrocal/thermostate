package com.thermostate.stadistics.domain;

import com.thermostate.roomtemperature.domain.events.RoomTemperatureRead;
import com.thermostate.shared.events.domain.AggregateRoot;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class State extends AggregateRoot {

    public final Integer externalTemp;
    public final Integer roomTemp;
    public final boolean isEnabled;

    public static State from(RoomTemperatureRead event) {
        return null;
    }
}
