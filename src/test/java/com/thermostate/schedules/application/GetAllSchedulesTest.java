package com.thermostate.schedules.application;

import com.thermostate.schedules.model.ScheduleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetAllSchedulesTest {
    ScheduleRepo scheduleRepo;
    GetAllSchedules sut;

    @BeforeEach
    public void setup() {
        scheduleRepo = mock(ScheduleRepo.class);
        sut = new GetAllSchedules(scheduleRepo);
    }

    @Test
    public void should_return_a_list_of_schedules() {
        //given
        //when
        sut.execute();
        //then
        verify(scheduleRepo).getAll();
    }
}