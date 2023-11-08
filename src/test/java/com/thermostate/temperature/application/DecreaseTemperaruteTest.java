package com.thermostate.temperature.application;

import com.google.common.eventbus.EventBus;
import com.thermostate.temperature.model.Temperature;
import com.thermostate.temperature.model.TemperatureChange;
import com.thermostate.temperature.model.TemperatureRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Integer decrementTemp = 100;
        Integer temp = 1600;
        when(temperatureRepo.getTemp()).thenReturn(new Temperature(temp));
        //when
        sut.execute(TemperatureChange.create(decrementTemp));
        //then
        verify(temperatureRepo).updateTemp(new Temperature(temp - decrementTemp));

        verify(eventBus).post(new Temperature(temp - decrementTemp));
    }
}
