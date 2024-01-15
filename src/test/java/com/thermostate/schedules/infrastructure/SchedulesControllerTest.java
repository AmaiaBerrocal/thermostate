package com.thermostate.schedules.infrastructure;

import com.thermostate.schedules.application.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SchedulesControllerTest {
    CreateSchedule createSchedule;
    GetScheduleById getScheduleById;
    GetAllSchedules getAllSchedules;
    DeleteSchedule deleteSchedule;
    UpdateSchedule updateSchedule;
    SchedulesController sut;

    @BeforeEach
    public void setup() {
        createSchedule = mock(CreateSchedule.class);
        getScheduleById = mock(GetScheduleById.class);
        getAllSchedules = mock(GetAllSchedules.class);
        deleteSchedule = mock(DeleteSchedule.class);
        updateSchedule = mock(UpdateSchedule.class);
        sut = new SchedulesController(createSchedule, getScheduleById, getAllSchedules, deleteSchedule, updateSchedule);
    }

    @Test
    public void should_call_application_layer_correctly_creating_schedule() {
        //given
        ScheduleCreateRequest req = new ScheduleCreateRequest(UUID.randomUUID(),"L,M", "08:00", "10:12", true, 15);
        //when
        sut.scheduleInsert(req);
        //then
        verify(createSchedule).execute(UUID.randomUUID(), req.weekDays, req.timeFrom, req.timeTo, req.active, req.minTemp);
    }

    @Test
    public void should_call_application_layer_correctly_getting_all_schedules() {
        //given
        //when
        sut.scheduleGetAll();
        //then
        verify(getAllSchedules).execute();
    }

    @Test
    public void should_call_application_layer_correctly_getting_schedule_by_id() {
        //given
        UUID id = UUID.randomUUID();
        //when
        sut.scheduleGetById(id);
        //then
        verify(getScheduleById).execute(id);
    }

    @Test
    public void should_call_application_layer_correctly_deleting_schedule_by_id() {
        //given
        UUID id = UUID.randomUUID();
        //when
        sut.deleteById(id);
        //then
        verify(deleteSchedule).execute(id);
    }
    @Test
    public void should_call_application_layer_correctly_updating_schedule() {
        //given
        ScheduleUpdateRequest req = new ScheduleUpdateRequest(UUID.randomUUID(), "L,M", "08:00", "10:12", true, 15);
        //when
        sut.scheduleUpdate(req);
        //then
        verify(updateSchedule).execute(req.id, req.weekDays, req.timeFrom, req.timeTo, req.active, req.minTemp);
    }
}
