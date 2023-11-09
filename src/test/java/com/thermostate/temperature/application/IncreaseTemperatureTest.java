package com.thermostate.temperature.application;

import com.thermostate.shared.events.SpringApplicationEventBus;
import com.thermostate.temperature.model.Temperature;
import com.thermostate.temperature.model.TemperatureChange;
import com.thermostate.temperature.model.TemperatureRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IncreaseTemperatureTest {
    TemperatureRepo temperatureRepo;
    IncreaseTemperature sut;
    SpringApplicationEventBus eventBus;


    @BeforeEach
    public void setup() {
        eventBus = mock(SpringApplicationEventBus.class);
        temperatureRepo = mock(TemperatureRepo.class);
        sut = new IncreaseTemperature(temperatureRepo, eventBus);
    }

    @Test
    public void should_increase_temperature_one_degree() {
        //given
        Integer incrementTemp = 100;
        Integer temp = 1600;
        var expected = new Temperature(temp);
        expected.change(TemperatureChange.create(incrementTemp));
        var events = expected.pullDomainEvents();
        when(temperatureRepo.getTemp()).thenReturn(new Temperature(temp));
        //when
        sut.execute(TemperatureChange.create(incrementTemp));
        //then
        var captor = ArgumentCaptor.forClass(Temperature.class);
        verify(temperatureRepo).updateTemp(captor.capture());
        assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(expected);
        var eventCaptor = ArgumentCaptor.forClass(List.class);
        verify(eventBus).publish(eventCaptor.capture());
        assertThat(eventCaptor.getValue())
                .usingRecursiveFieldByFieldElementComparatorOnFields("amount").isEqualTo(events);
    }
}
