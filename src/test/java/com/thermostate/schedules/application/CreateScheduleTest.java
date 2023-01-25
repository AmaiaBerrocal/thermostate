package com.thermostate.schedules.application;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleRepo;
import com.thermostate.shared.ClientError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class CreateScheduleTest {
    ScheduleRepo scheduleRepo;
    CreateSchedule sut;

    @BeforeEach
    public void setup() {
        scheduleRepo = mock(ScheduleRepo.class);
        sut = new CreateSchedule(scheduleRepo);
    }

    @Test
    public void should_create_schedule_if_data_are_correct() {
        //given
        String weekDays = "L,M,X,J";
        String timeFrom = "19:00";
        String timeTo = "23:59";
        Boolean active = true;
        Integer minTemp = 16;
        //when
        sut.execute(weekDays, timeFrom, timeTo, active, minTemp);
        //then
        verify(scheduleRepo).create(new Schedule(null, weekDays, timeFrom, timeTo, active, minTemp, null));
    }

    @Test
    public void should_not_create_schedule_if_weekdays_is_incorrect() {
        //given
        String weekDays = "L,M,XJ";
        String timeFrom = "19:00";
        String timeTo = "23:59";
        Boolean active = true;
        Integer minTemp = 16;
        //when
        assertThatThrownBy( () -> sut.execute(weekDays, timeFrom, timeTo, active, minTemp)
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(scheduleRepo);
    }

    @Test
    public void should_not_create_schedule_if_weekDays_is_incorrect_because_incorrect_day() {
        //given
        String weekDays = "L,M,X,F";
        String timeFrom = "19:00";
        String timeTo = "23:59";
        Boolean active = true;
        Integer minTemp = 16;
        //when
        assertThatThrownBy( () -> sut.execute(weekDays, timeFrom, timeTo, active, minTemp)
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(scheduleRepo);
    }

    @Test
    public void should_not_create_schedule_if_timefrom_is_incorrect() {
        //given
        String weekDays = "L,M,X,J";
        String timeFrom = null;
        String timeTo = "23:59";
        Boolean active = true;
        Integer minTemp = 16;
        //when
        assertThatThrownBy( () -> sut.execute(weekDays, timeFrom, timeTo, active, minTemp)
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(scheduleRepo);
    }

    @Test
    public void should_not_create_schedule_if_timeto_is_incorrect() {
        //given
        String weekDays = "L,M,X,J";
        String timeFrom = "23:59";
        String timeTo = null;
        Boolean active = true;
        Integer minTemp = 16;
        //when
        assertThatThrownBy( () -> sut.execute(weekDays, timeFrom, timeTo, active, minTemp)
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(scheduleRepo);
    }

    @Test
    public void should_not_create_schedule_if_activate_is_incorrect() {
        //given
        String weekDays = "L,M,X,J";
        String timeFrom = "23:59";
        String timeTo = "15:00";
        Boolean active = null;
        Integer minTemp = 16;
        //when
        assertThatThrownBy( () -> sut.execute(weekDays, timeFrom, timeTo, active, minTemp)
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(scheduleRepo);
    }

    @Test
    public void should_not_create_schedule_if_mintemp_is_incorrect() {
        //given
        String weekDays = "L,M,X,J";
        String timeFrom = "23:59";
        String timeTo = "15:00";
        Boolean active = false;
        Integer minTemp = null;
        //when
        assertThatThrownBy( () -> sut.execute(weekDays, timeFrom, timeTo, active, minTemp)
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(scheduleRepo);
    }
}
