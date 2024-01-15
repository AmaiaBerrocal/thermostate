package com.thermostate.shared;

import com.thermostate.externaltemperature.application.GetExternalTemperature;
import com.thermostate.roomtemperature.application.GetRoomTemperature;
import com.thermostate.schedules.application.GetAllSchedules;
import com.thermostate.schedules.application.ScheduleChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.awaitility.Awaitility.given;
import static org.mockito.Mockito.*;

class SchedulingConfiguratorTest {
    GetRoomTemperature getRoomTemperature;
    GetExternalTemperature getExternalTemperature;
    GetAllSchedules getAllSchedules;
    ScheduleChecker scheduleChecker;
    SchedulingConfigurator sut;

    @BeforeEach
    public void setup() {
        getRoomTemperature = mock(GetRoomTemperature.class);
        getExternalTemperature = mock(GetExternalTemperature.class);
        getAllSchedules = mock(GetAllSchedules.class);
        scheduleChecker = mock(ScheduleChecker.class);
        sut = new SchedulingConfigurator(getRoomTemperature, getExternalTemperature, getAllSchedules, scheduleChecker);
    }

    @Test
    void should_call_application_layer_correctly() {
        when(getAllSchedules.execute()).thenReturn(List.of());
        //when
        sut.scheduleFixedDelayTask();
        //then
        verify(getRoomTemperature).execute();
        verify(getAllSchedules).execute();
        verify(scheduleChecker).execute(List.of());

    }

    @Test
    void should_call_application_layer_correctly_for_external_temp() {
        //when
        sut.scheduleFixedDelayExternalTemp();
        //then
        verify(getExternalTemperature).execute();
    }
}
