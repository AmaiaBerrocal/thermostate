package com.thermostate.schedules.infrastructure;

import com.thermostate.schedules.application.CreateSchedule;
import com.thermostate.schedules.application.GetAllSchedules;
import com.thermostate.schedules.application.GetScheduleById;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SchedulesControllerTest {
    CreateSchedule createSchedule;
    GetScheduleById getScheduleById;
    GetAllSchedules getAllSchedules;
    SchedulesController sut;

    @BeforeEach
    public void setup() {
        createSchedule = mock(CreateSchedule.class);
        getScheduleById = mock(GetScheduleById.class);
        getAllSchedules = mock(GetAllSchedules.class);
        sut = new SchedulesController(createSchedule, getScheduleById, getAllSchedules);
    }

    @Test
    public void should_call_application_layer_correctly() {
        //given
        ScheduleCreateRequest req = new ScheduleCreateRequest(LocalDate.of(2020,01,03), LocalDate.of(2023,03,16), "08:00", "10:12", true, 15);
        //when
        sut.scheduleInsert(req);
        //then
        verify(createSchedule).execute(req.dateFrom, req.dateTo, req.timeFrom, req.timeTo, req.active, req.minTemp);
    }

}
