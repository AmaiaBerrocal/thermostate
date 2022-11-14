package com.thermostate.temperature.infrastructure;

import com.thermostate.temperature.application.DecreaseTemperature;
import com.thermostate.temperature.application.IncreaseTemperature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TemperatureControllerTest {
    IncreaseTemperature increaseTemperature;
    DecreaseTemperature decreaseTemperature;
    TemperatureController sut;

    @BeforeEach
    public void setup() {
        increaseTemperature = mock(IncreaseTemperature.class);
        decreaseTemperature = mock(DecreaseTemperature.class);
        sut = new TemperatureController(increaseTemperature, decreaseTemperature);
    }

    @Test
    public void should_call_application_layer_correctly_increasing_temp() {
        //given
        TempUpdateRequest req = new TempUpdateRequest(15);
        //when
        sut.tempIncrement(req);
        //then
        verify(increaseTemperature).execute(req.temperature);
    }

    @Test
    public void should_call_application_layer_correctly_decreasing_temp() {
        //given
        TempUpdateRequest req = new TempUpdateRequest(15);
        //when
        sut.tempDecrement(req);
        //then
        verify(decreaseTemperature).execute(req.temperature);
    }

}
