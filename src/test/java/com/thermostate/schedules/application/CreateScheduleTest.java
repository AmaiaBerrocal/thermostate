package com.thermostate.schedules.application;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleRepo;
import com.thermostate.shared.ClientError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.thermostate.schedules.model.ScheduleObjectMother.givenScheduleForDays;
import static com.thermostate.schedules.model.ScheduleObjectMother.givenScheduleWithActivation;
import static com.thermostate.schedules.model.ScheduleObjectMother.givenScheduleWithMinTemp;
import static com.thermostate.schedules.model.ScheduleObjectMother.givenScheduleWithTimes;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.then;
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
        String weekDays = "L,M,X,J,V,S,D";
        var schedule = givenScheduleForDays(weekDays);
        //when
        sut.execute(weekDays, schedule.timeFrom, schedule.timeTo, schedule.active, schedule.minTemp);
        //then
        then(scheduleRepo).should().create(
            new Schedule(null, weekDays, schedule.timeFrom, schedule.timeTo, schedule.active, schedule.minTemp, null));
    }

    @Test
    public void should_not_create_schedule_if_weekdays_is_incorrect() {
        String weekDays = "L,M,XJ";
        thenThrownBy( () ->
                givenScheduleForDays(weekDays))
            .isInstanceOf(ClientError.class);
        then(scheduleRepo).shouldHaveNoInteractions();
    }

    @Test
    public void should_not_create_schedule_if_weekDays_is_incorrect_because_incorrect_day() {
        String weekDays = "L,M,X,F";
        thenThrownBy( () ->
            givenScheduleForDays(weekDays))
            .isInstanceOf(ClientError.class);
        then(scheduleRepo).shouldHaveNoInteractions();
    }

    @Test
    public void should_not_create_schedule_if_timefrom_is_incorrect() {
        thenThrownBy( () ->
                givenScheduleWithTimes(null, "23:59"))
            .isInstanceOf(ClientError.class);
        then(scheduleRepo).shouldHaveNoInteractions();
    }

    @Test
    public void should_not_create_schedule_if_timeto_is_incorrect() {
        thenThrownBy( () ->
                givenScheduleWithTimes("00:00", null))
            .isInstanceOf(ClientError.class);
        then(scheduleRepo).shouldHaveNoInteractions();
    }

    @Test
    public void should_not_create_schedule_if_activate_is_incorrect() {
        thenThrownBy( () ->
                givenScheduleWithActivation(null))
            .isInstanceOf(ClientError.class);
        then(scheduleRepo).shouldHaveNoInteractions();
    }

    @Test
    public void should_not_create_schedule_if_mintemp_is_incorrect() {
        thenThrownBy( () ->
                givenScheduleWithMinTemp(null))
            .isInstanceOf(ClientError.class);
        then(scheduleRepo).shouldHaveNoInteractions();
    }
}
