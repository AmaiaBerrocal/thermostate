package com.thermostate.schedules.infrastructure;

import com.thermostate.schedules.application.CreateSchedule;
import com.thermostate.schedules.application.DeleteSchedule;
import com.thermostate.schedules.application.GetAllSchedules;
import com.thermostate.schedules.application.GetScheduleById;
import com.thermostate.schedules.model.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SchedulesControllerTest {
    CreateSchedule createSchedule;
    GetScheduleById getScheduleById;
    GetAllSchedules getAllSchedules;

    DeleteSchedule deleteSchedule;
    SchedulesController sut;

    @BeforeEach
    public void setup() {
        createSchedule = mock(CreateSchedule.class);
        getScheduleById = mock(GetScheduleById.class);
        getAllSchedules = mock(GetAllSchedules.class);
        deleteSchedule = mock(DeleteSchedule.class);
        sut = new SchedulesController(createSchedule, getScheduleById, getAllSchedules, deleteSchedule);
    }

    @Test
    public void should_call_application_layer_correctly_creating_schedule() {
        //given
        ScheduleCreateRequest req = new ScheduleCreateRequest(LocalDate.of(2020,01,03), LocalDate.of(2023,03,16), "08:00", "10:12", true, 15);
        //when
        sut.scheduleInsert(req);
        //then
        verify(createSchedule).execute(req.dateFrom, req.dateTo, req.timeFrom, req.timeTo, req.active, req.minTemp);
    }

    @Test
    public void should_call_application_layer_correctly_getting_all_schedules() {
        //given
        List<Schedule> schedules= new ArrayList<Schedule>();
        schedules.add(new Schedule(1, LocalDate.of(2020, 01, 03), LocalDate.of(2023, 03, 16), "08:00", "10:12", true, 15, LocalDate.of(2019, 01, 03)));
        schedules.add(new Schedule(2, LocalDate.of(2021, 01, 03), LocalDate.of(2023, 03, 16), "08:00", "10:12", true, 15, LocalDate.of(2019, 01, 03)));
        schedules.add(new Schedule(3, LocalDate.of(2023, 02, 03), LocalDate.of(2023, 03, 16), "08:00", "10:12", true, 15, LocalDate.of(2019, 01, 03)));
        //when
        sut.scheduleGetAll();
        //then
        verify(getAllSchedules).execute();
    }

    @Test
    public void should_call_application_layer_correctly_getting_schedule_by_id() {
        //given
        Integer id = 1;
        //when
        sut.scheduleGetById(id);
        //then
        verify(getScheduleById).execute(id);
    }

    @Test
    public void should_call_application_layer_correctly_deleting_schedule_by_id() {
        //given
        Integer id = 1;
        //when
        sut.deleteById(id);
        //then
        verify(deleteSchedule).execute(id);
    }
}
