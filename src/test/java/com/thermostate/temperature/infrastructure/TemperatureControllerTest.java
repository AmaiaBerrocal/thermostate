package com.thermostate.temperature.infrastructure;

import com.thermostate.temperature.application.DecreaseTemperature;
import com.thermostate.temperature.application.GetTemperature;
import com.thermostate.temperature.application.IncreaseTemperature;
import com.thermostate.temperature.model.TemperatureChange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TemperatureControllerTest {
    IncreaseTemperature increaseTemperature;
    DecreaseTemperature decreaseTemperature;
    GetTemperature getTemperature;
    TemperatureController sut;

    @BeforeEach
    public void setup() {
        increaseTemperature = mock(IncreaseTemperature.class);
        decreaseTemperature = mock(DecreaseTemperature.class);
        getTemperature = mock(GetTemperature.class);
        sut = new TemperatureController(increaseTemperature, decreaseTemperature, getTemperature);
    }

    @Test
    void should_call_application_layer_correctly_increasing_temp() {
        //givenDomainEventSubscriber
        TempUpdateRequest req = new TempUpdateRequest(15);
        //when
        sut.tempIncrement(req);
        //then
        verify(increaseTemperature).execute(TemperatureChange.create(req.temperature));
    }

    @Test
    void should_call_application_layer_correctly_decreasing_temp() {
        //given
        TempUpdateRequest req = new TempUpdateRequest(15);
        //when
        sut.tempDecrement(req);
        //then
        verify(decreaseTemperature).execute(TemperatureChange.create(-req.temperature));
    }
}
