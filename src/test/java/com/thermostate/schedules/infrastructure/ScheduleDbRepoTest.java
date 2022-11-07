package com.thermostate.schedules.infrastructure;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.shared.DbUtils;
import com.thermostate.users.infrastucture.UserDbRepo;
import com.thermostate.users.model.User;
import com.thermostate.users.model.UserObjectMother;
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
    public void schedule_table_should_be_create() throws SQLException {
        //given
        String sql = "CREATE TABLE IF NOT EXISTS SCHEDULES (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DATE_FROM DATE NOT NULL," +
                "DATE_TO DATE NOT NULL," +
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
        Schedule schedule = new Schedule(1, LocalDate.of(2020, 01, 03), LocalDate.of(2023, 03, 16), "08:00", "10:12", true, 15, LocalDate.of(2019, 01, 03));
        String sql = "INSERT INTO SCHEDULES (DATE_FROM, DATE_TO, TIME_FROM, TIME_TO, ACTIVE, MIN_TEMP, CREATED_AT) VALUES (" +
                "'" + schedule.dateFrom() +
                "','" + schedule.dateTo() +
                "','" + schedule.timeFrom() +
                "','" + schedule.timeTo() +
                "',true" +
                "," + schedule.minTemp() +
                ", CURRENT_DATE)";
        //when
        sut.create(schedule);
        //then
        verify(dbUtils).executeUpdate(sql);
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
