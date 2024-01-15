package com.thermostate.targettemperature.application;

import com.thermostate.brain.domain.ThermostatAdapter;
import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.schedules.model.events.EventBus;
import com.thermostate.shared.domain.Temperature;
import com.thermostate.targettemperature.model.TemperatureChange;
import com.thermostate.targettemperature.model.event.TargetTemperatureChanged;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class IncreaseTemperatureTest {
    ThermostateStatus status;
    IncreaseTargetTemperature sut;
    EventBus eventBus;
    ThermostatAdapter adapter;


    @BeforeEach
    public void setup() {
        eventBus = mock(EventBus.class);
        adapter = mock(ThermostatAdapter.class);
        status = new ThermostateStatus(adapter);
        status.setTargetTemperature(new Temperature(1600));
        sut = new IncreaseTargetTemperature(eventBus);
    }

    @Test
    public void should_increase_temperature_one_degree() {
        //given
        Integer incrementTemp = 100;
        //when
        sut.execute(TemperatureChange.create(incrementTemp));
        //then
        var eventCaptor = ArgumentCaptor.forClass(TargetTemperatureChanged.class);
        verify(eventBus).emit(eventCaptor.capture());
        assertThat(eventCaptor.getValue().amount).isEqualTo(incrementTemp);
    }
}
