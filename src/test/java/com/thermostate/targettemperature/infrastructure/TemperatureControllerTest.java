package com.thermostate.targettemperature.infrastructure;

import com.thermostate.targettemperature.application.DecreaseTargetTemperature;
import com.thermostate.targettemperature.application.GetTargetTemperature;
import com.thermostate.targettemperature.application.IncreaseTargetTemperature;
import com.thermostate.targettemperature.model.TemperatureChange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TemperatureControllerTest {
    IncreaseTargetTemperature increaseTargetTemperature;
    DecreaseTargetTemperature decreaseTargetTemperature;
    GetTargetTemperature getTargetTemperature;
    TargetTemperatureController sut;

    @BeforeEach
    public void setup() {
        increaseTargetTemperature = mock(IncreaseTargetTemperature.class);
        decreaseTargetTemperature = mock(DecreaseTargetTemperature.class);
        getTargetTemperature = mock(GetTargetTemperature.class);
        sut = new TargetTemperatureController(increaseTargetTemperature, decreaseTargetTemperature, getTargetTemperature);
    }

    @Test
    void should_call_application_layer_correctly_increasing_temp() {
        //givenDomainEventSubscriber
        TempUpdateRequest req = new TempUpdateRequest(15);
        //when
        sut.tempIncrement(req);
        //then
        verify(increaseTargetTemperature).execute(TemperatureChange.create(req.temperature));
    }

    @Test
    void should_call_application_layer_correctly_decreasing_temp() {
        //given
        TempUpdateRequest req = new TempUpdateRequest(15);
        //when
        sut.tempDecrement(req);
        //then
        verify(decreaseTargetTemperature).execute(TemperatureChange.create(-req.temperature));
    }
}
