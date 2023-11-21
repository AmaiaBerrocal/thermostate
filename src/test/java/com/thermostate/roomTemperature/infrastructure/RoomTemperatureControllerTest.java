package com.thermostate.roomTemperature.infrastructure;

import com.thermostate.roomtemperature.application.GetRoomTemperature;
import com.thermostate.roomtemperature.infrastructure.RoomTemperatureController;
import com.thermostate.roomtemperature.model.RoomTemperature;
import com.thermostate.shared.ValueResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class RoomTemperatureControllerTest {
    GetRoomTemperature getRoomTemperature;
    RoomTemperatureController sut;

    @BeforeEach
    public void setup() {
        getRoomTemperature = mock(GetRoomTemperature.class);
        sut = new RoomTemperatureController(getRoomTemperature);
    }

    @Test
    void should_call_application_layer_correctly() {
        //given
        RoomTemperature temperature = RoomTemperature.create("15278");
        when(getRoomTemperature.execute()).thenReturn(temperature);
        //when
        ValueResponse<RoomTemperature> actual = sut.getTemp();
        //then
        assertThat(actual.value()).isEqualTo(temperature);
    }
}
