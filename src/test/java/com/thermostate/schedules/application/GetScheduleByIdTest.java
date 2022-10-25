package com.thermostate.schedules.application;


import com.thermostate.schedules.model.ScheduleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetScheduleByIdTest {
    ScheduleRepo scheduleRepo;
    GetScheduleById sut;

    @BeforeEach
    public void setup() {
        scheduleRepo = mock(ScheduleRepo.class);
        sut = new GetScheduleById(scheduleRepo);
    }

    @Test
    public void should_return_an_schedule() {
        //given
        Integer id = 8;
        //when
        sut.execute(id);
        //then
        verify(scheduleRepo).getById(id);
    }
}