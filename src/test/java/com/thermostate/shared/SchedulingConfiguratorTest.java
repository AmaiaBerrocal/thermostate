package com.thermostate.shared;

import com.thermostate.roomtemperature.application.GetRoomTemperature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SchedulingConfiguratorTest {
    GetRoomTemperature getRoomTemperature;
    SchedulingConfigurator sut;

    @BeforeEach
    public void setup() {
        getRoomTemperature = mock(GetRoomTemperature.class);
        sut = new SchedulingConfigurator(getRoomTemperature);
    }

    @Test
    void should_call_application_layer_correctly() {
        //when
        sut.scheduleFixedDelayTask();
        //then
        verify(getRoomTemperature).execute();
    }
}
