package com.thermostate.schedules.infrastructure;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.shared.DbUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ScheduleDbRepoTest {
    DbUtils dbUtils;
    ScheduleDbRepo sut;

    @BeforeEach
    public void setup() {
        dbUtils = mock(DbUtils.class);
        sut = new ScheduleDbRepo(dbUtils);
    }


    @Test
    public void table_should_be_created() throws SQLException {
        //given
        String sql = "CREATE TABLE IF NOT EXISTS SCHEDULES (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "WEEKDAYS TEXT NOT NULL," +
                "TIME_FROM TEXT NOT NULL," +
                "TIME_TO TEXT NOT NULL," +
                "ACTIVE BOOLEAN," +
                "MIN_TEMP INTEGER," +
                "CREATED_AT DATE);";
        //when
        sut.createScheduleTable();
        //then
        verify(dbUtils).executeUpdate(sql);
    }

    @Test
    public void schedule_should_be_insert() {
        //given
        Schedule schedule = new Schedule(1, "L,M", "08:00", "10:12", true, 15, LocalDate.of(2019, 01, 03));
        String sql = "INSERT INTO SCHEDULES (WEEKDAYS, TIME_FROM, TIME_TO, ACTIVE, MIN_TEMP, CREATED_AT) VALUES (" +
                "'" + schedule.weekDays() +
                "','" + schedule.timeFrom() +
                "','" + schedule.timeTo() +
                "', 1" +
                "," + schedule.minTemp() +
                ", CURRENT_DATE)";
        //when
        sut.create(schedule);
        //then
        verify(dbUtils).executeUpdate(sql);
    }

    @Test
    public void should_return_list_of_schedules(){
        //given
        String sql = "SELECT * FROM SCHEDULES";
        //when
        sut.getAll();
        //then
        verify(dbUtils).executeQuery(sql);
    }

    @Test
    public void should_return_schedule_by_id() {
        //given
        Integer id = 1;
        String sql = "SELECT * FROM SCHEDULES WHERE ID = " + id;
        //when
        sut.getById(id);
        //then
        verify(dbUtils).executeQuery(sql);
    }

    @Test
    public void should_update_an_existing_schedule() {
        //given
        Schedule schedule = new Schedule(1,
                "L,M",
            "08:00",
                "10:12",
                true,
                15,
                LocalDate.of(2019, 01, 03));
        String sql = "UPDATE SCHEDULES SET " +
                "WEEKDAYS = '" + schedule.weekDays() + "', " +
                "TIME_FROM = '" + schedule.timeFrom() + "', " +
                "TIME_TO = '" + schedule.timeTo() + "', " +
                "ACTIVE = '1', " +
                "MIN_TEMP = " + schedule.minTemp() + " " +
                "WHERE ID = " + schedule.id();
        //when
        sut.update(schedule);
        //then
        verify(dbUtils).executeUpdate(sql);
    }


    @Test
    public void should_delete_schedule_by_id() {
        //given
        Integer id = 1;
        String sql = "DELETE FROM SCHEDULES WHERE ID='" + id + "'";
        //when
        sut.deleteById(id);
        //then
        verify(dbUtils).executeUpdate(sql);
    }
}
