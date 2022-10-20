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
        LocalDate dateFrom = LocalDate.of(2023, 3, 15);
        LocalDate dateTo = LocalDate.of(2033, 3, 5);;
        String timeFrom = "19:00";
        String timeTo = "23:59";
        Boolean active = true;
        Integer minTemp = 16;
        //when
        sut.execute(id);
        //then
        verify(scheduleRepo).getById(id);
    }
}