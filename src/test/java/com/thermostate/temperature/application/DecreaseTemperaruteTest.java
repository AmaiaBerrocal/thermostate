package com.thermostate.temperature.application;

import com.thermostate.temperature.model.Temperature;
import com.thermostate.temperature.model.TemperatureRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DecreaseTemperaruteTest {
    TemperatureRepo temperatureRepo;
    DecreaseTemperature sut;

    @BeforeEach
    public void setup() {
        temperatureRepo = mock(TemperatureRepo.class);
        sut = new DecreaseTemperature(temperatureRepo);
    }

    @Test
    public void should_decrease_temperature_one_degree() {
        //given
        Integer decrementTemp = 100;
        Integer temp = 1600;
        when(temperatureRepo.getTemp()).thenReturn(new Temperature(temp));
        //when
        sut.execute(decrementTemp);
        //then
        Temperature temperature = new Temperature(decrementTemp);
        verify(temperatureRepo).updateTemp(new Temperature(temp - decrementTemp));
    }
}
