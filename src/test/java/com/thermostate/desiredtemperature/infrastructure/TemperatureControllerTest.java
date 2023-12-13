package com.thermostate.desiredtemperature.infrastructure;

import com.thermostate.desiredtemperature.application.DecreaseTemperature;
import com.thermostate.desiredtemperature.application.GetTemperature;
import com.thermostate.desiredtemperature.application.IncreaseTemperature;
import com.thermostate.desiredtemperature.model.TemperatureChange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TemperatureControllerTest {
    IncreaseTemperature increaseTemperature;
    DecreaseTemperature decreaseTemperature;
    GetTemperature getTemperature;
    DesiredTemperatureController sut;

    @BeforeEach
    public void setup() {
        increaseTemperature = mock(IncreaseTemperature.class);
        decreaseTemperature = mock(DecreaseTemperature.class);
        getTemperature = mock(GetTemperature.class);
        sut = new DesiredTemperatureController(increaseTemperature, decreaseTemperature, getTemperature);
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
