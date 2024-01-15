package com.thermostate.schedules.application;

import com.thermostate.schedules.model.ScheduleRepo;
import com.thermostate.schedules.model.events.EventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DeleteScheduleTest {
    ScheduleRepo scheduleRepo;
    DeleteSchedule sut;
    EventBus eventBus;
    @BeforeEach
    public void setup() {
        scheduleRepo = mock(ScheduleRepo.class);
        eventBus = mock(EventBus.class);
        sut = new DeleteSchedule(scheduleRepo, eventBus);
    }

    @Test
    public void should_delete_a_schedule() {
        //given
        UUID id = UUID.randomUUID();
        //when
        sut.execute(id);
        //then
        verify(scheduleRepo).deleteById(id);
    }
}
