package com.thermostate.shared;

import com.thermostate.externaltemperature.application.GetExternalTemperature;
import com.thermostate.roomtemperature.application.GetRoomTemperature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SchedulingConfiguratorTest {
    GetRoomTemperature getRoomTemperature;
    GetExternalTemperature getExternalTemperature;
    SchedulingConfigurator sut;

    @BeforeEach
    public void setup() {
        getRoomTemperature = mock(GetRoomTemperature.class);
        getExternalTemperature = mock(GetExternalTemperature.class);
        sut = new SchedulingConfigurator(getRoomTemperature, getExternalTemperature);
    }

    @Test
    void should_call_application_layer_correctly() {
        //when
        sut.scheduleFixedDelayTask();
        //then
        verify(getRoomTemperature).execute();
    }

    @Test
    void should_call_application_layer_correctly_for_external_temp() {
        //when
        sut.scheduleFixedDelayExternalTemp();
        //then
        verify(getExternalTemperature).execute();
    }
}
