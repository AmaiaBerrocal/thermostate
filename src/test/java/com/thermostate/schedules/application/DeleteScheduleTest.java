package com.thermostate.schedules.application;

import com.thermostate.schedules.model.ScheduleRepo;
import com.thermostate.shared.events.EventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Integer id = 8;
        //when
        sut.execute(id);
        //then
        verify(scheduleRepo).deleteById(id);
    }
}
