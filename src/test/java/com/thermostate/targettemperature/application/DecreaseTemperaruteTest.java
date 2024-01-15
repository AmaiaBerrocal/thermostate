package com.thermostate.targettemperature.application;

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

public class DecreaseTemperaruteTest {
    ThermostateStatus status;
    IncreaseTargetTemperature sut;
    EventBus eventBus;


    @BeforeEach
    public void setup() {
        eventBus = mock(EventBus.class);
        status = new ThermostateStatus();
        status.setTargetTemperature(new Temperature(1600));
        sut = new IncreaseTargetTemperature(eventBus);
    }

    @Test
    public void should_increase_temperature_one_degree() {
        //given
        Integer decrementTemp = -100;
        var expected = new Temperature(1500);
        status.setTargetTemperature(new Temperature(1600));
        //when
        sut.execute(TemperatureChange.create(decrementTemp));
        //then
        assertThat(status.getTargetTemperature().getTemp()).isEqualTo(expected.getTemp());
        var eventCaptor = ArgumentCaptor.forClass(TargetTemperatureChanged.class);
        verify(eventBus).emit(eventCaptor.capture());
        assertThat(eventCaptor.getValue().amount).isEqualTo(decrementTemp);
    }
}
