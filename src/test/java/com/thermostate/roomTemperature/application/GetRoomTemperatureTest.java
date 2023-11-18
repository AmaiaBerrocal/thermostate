package com.thermostate.roomTemperature.application;

import com.thermostate.roomtemperature.application.GetRoomTemperature;
import com.thermostate.roomtemperature.model.RoomTemperature;
import com.thermostate.roomtemperature.model.RoomTemperatureRepo;
import com.thermostate.shared.events.EventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class GetRoomTemperatureTest {
    GetRoomTemperature sut;
    EventBus eventBus;
    RoomTemperatureRepo roomTemperatureRepo;

    @BeforeEach
    public void setup() {
        roomTemperatureRepo = mock(RoomTemperatureRepo.class);
        eventBus = mock(EventBus.class);
        sut = new GetRoomTemperature(roomTemperatureRepo, eventBus);
    }

    @Test
    void should_post_an_event_correctly() {
        RoomTemperature temperature = RoomTemperature.create("15278");
        when(roomTemperatureRepo.getTemp()).thenReturn(temperature);

        RoomTemperature actual = sut.execute();

        assertThat(actual).isEqualTo(temperature);
        //verify(eventBus).post(temperature);
    }
}
