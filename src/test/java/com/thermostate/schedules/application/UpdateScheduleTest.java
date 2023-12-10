package com.thermostate.schedules.application;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleRepo;
import com.thermostate.schedules.model.events.EventBus;
import com.thermostate.shared.ClientError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UpdateScheduleTest {

    ScheduleRepo scheduleRepo;
    UpdateSchedule sut;
    EventBus eventBus;

    @BeforeEach
    public void setup() {
        scheduleRepo = mock(ScheduleRepo.class);
        eventBus = mock(EventBus.class);
        sut = new UpdateSchedule(scheduleRepo, eventBus);
    }

    @Test
    public void should_update_schedule_if_data_are_correct() {
        //given
        Integer id = 1;
        String weekDays = "L,M,X,J,V,S,D";
        String timeFrom = "19:00";
        String timeTo = "23:59";
        Boolean active = true;
        Integer minTemp = 16;
        //when
        sut.execute(id, weekDays, timeFrom, timeTo, active, minTemp);
        //then
        verify(scheduleRepo).update(new Schedule(id, weekDays, timeFrom, timeTo, active, minTemp, null));
    }

    @Test
    public void should_not_update_schedule_if_weekDays_is_incorrect() {
        //given
        Integer id = 1;
        String weekDays = "L,MX";
        String timeFrom = "19:00";
        String timeTo = "23:59";
        Boolean active = true;
        Integer minTemp = 16;
        //when
        assertThatThrownBy( () -> sut.execute(id, weekDays, timeFrom, timeTo, active, minTemp)
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(scheduleRepo);
    }

    @Test
    public void should_not_update_schedule_if_weekday_is_incorrect_because_an_incorrect_day() {
        //given
        Integer id = 1;
        String weekDays = "L,M,R";
        String timeFrom = "19:00";
        String timeTo = "23:59";
        Boolean active = true;
        Integer minTemp = 16;
        //when
        assertThatThrownBy( () -> sut.execute(id, weekDays, timeFrom, timeTo, active, minTemp)
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(scheduleRepo);
    }

    @Test
    public void should_not_update_schedule_if_timefrom_is_incorrect() {
        //given
        Integer id = 1;
        String weekDays = "L,M,X";
        String timeFrom = null;
        String timeTo = "23:59";
        Boolean active = true;
        Integer minTemp = 16;
        //when
        assertThatThrownBy( () -> sut.execute(id, weekDays, timeFrom, timeTo, active, minTemp)
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(scheduleRepo);
    }

    @Test
    public void should_not_update_schedule_if_timeto_is_incorrect() {
        //given
        Integer id = 1;
        String weekDays = "L,M,X";
        String timeFrom = "23:59";
        String timeTo = null;
        Boolean active = true;
        Integer minTemp = 16;
        //when
        assertThatThrownBy( () -> sut.execute(id, weekDays, timeFrom, timeTo, active, minTemp)
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(scheduleRepo);
    }

    @Test
    public void should_not_update_schedule_if_activate_is_incorrect() {
        //given
        Integer id = 1;
        String weekDays = "L,M,X";
        String timeFrom = "08:00";
        String timeTo = "23:15";
        Boolean active = null;
        Integer minTemp = 16;
        //when
        assertThatThrownBy( () -> sut.execute(id, weekDays, timeFrom, timeTo, active, minTemp)
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(scheduleRepo);
    }

    @Test
    public void should_not_update_schedule_if_mintemp_is_incorrect() {
        //given
        Integer id = 1;
        String weekDays = "L,M,X";
        String timeFrom = "08:00";
        String timeTo = "23:15";
        Boolean active = true;
        Integer minTemp = null;
        //when
        assertThatThrownBy(() -> sut.execute(id, weekDays, timeFrom, timeTo, active, minTemp)
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(scheduleRepo);
    }
}
