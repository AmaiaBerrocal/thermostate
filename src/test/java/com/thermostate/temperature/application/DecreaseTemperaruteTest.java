package com.thermostate.temperature.application;

import com.thermostate.schedules.model.events.EventBus;
import com.thermostate.temperature.model.Temperature;
import com.thermostate.temperature.model.TemperatureChange;
import com.thermostate.temperature.model.TemperatureRepo;
import com.thermostate.temperature.model.event.TargetTemperatureChanged;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DecreaseTemperaruteTest {
    TemperatureRepo temperatureRepo;
    DecreaseTemperature sut;
    EventBus eventBus;

    @BeforeEach
    public void setup() {
        temperatureRepo = mock(TemperatureRepo.class);
        eventBus = mock(EventBus.class);
        sut = new DecreaseTemperature(temperatureRepo, eventBus);
    }

    @Test
    public void should_decrease_temperature_one_degree() {
        //given
        Integer decrementTemp = -100;
        Integer temp = 1600;
        var expected = new Temperature(1500);
        when(temperatureRepo.getTemp()).thenReturn(new Temperature(temp));
        //when
        sut.execute(TemperatureChange.create(decrementTemp));
        //then
        var captor = ArgumentCaptor.forClass(Temperature.class);
        verify(temperatureRepo).updateTemp(captor.capture());
        assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(expected);
        var eventCaptor = ArgumentCaptor.forClass(TargetTemperatureChanged.class);
        verify(eventBus).emit(eventCaptor.capture());
        assertThat(eventCaptor.getValue().amount).isEqualTo(decrementTemp);
    }
}
