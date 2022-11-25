package com.thermostate.temperature.application;

import com.thermostate.temperature.model.Temperature;
import com.thermostate.temperature.model.TemperatureRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IncreaseTemperatureTest {
    TemperatureRepo temperatureRepo;
    IncreaseTemperature sut;

    @BeforeEach
    public void setup() {
        temperatureRepo = mock(TemperatureRepo.class);
        sut = new IncreaseTemperature(temperatureRepo);
    }

    @Test
    public void should_increase_temperature_one_degree() {
        //given
        Integer incrementTemp = 100;
        Integer temp = 1600;
        when(temperatureRepo.getTemp()).thenReturn(new Temperature(temp));
        //when
        sut.execute(incrementTemp);
        //then
        Temperature temperature = new Temperature(incrementTemp);
        verify(temperatureRepo).updateTemp(new Temperature(temp + incrementTemp));
    }
}
