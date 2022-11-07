package com.thermostate.schedules.application;

import com.thermostate.schedules.model.ScheduleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DeleteScheduleTest {
    ScheduleRepo scheduleRepo;
    DeleteSchedule sut;

    @BeforeEach
    public void setup() {
        scheduleRepo = mock(ScheduleRepo.class);
        sut = new DeleteSchedule(scheduleRepo);
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
