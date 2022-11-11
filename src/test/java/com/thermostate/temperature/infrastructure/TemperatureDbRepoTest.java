package com.thermostate.temperature.infrastructure;

import com.thermostate.shared.DbUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TemperatureDbRepoTest {
    DbUtils dbUtils;
    TemperatureDbRepo sut;

    @BeforeEach
    public void setup() {
        dbUtils = mock(DbUtils.class);
        sut = new TemperatureDbRepo(dbUtils);
    }

    @Test
    public void temperature_table_should_be_create() throws SQLException {
        //given
        String sql = "CREATE TABLE IF NOT EXISTS TEMPERATURE (" +
                "ID INTEGER NOT NULL," +
                "TEMP INTEGER NOT NULL);";
        sql += "INSERT INTO TEMPERATURE (ID, TEMP) VALUES (" + 1 + ", " + (16 * 100) + ")";
        //when
        sut.createTemperatureTable();
        //then
        verify(dbUtils).executeUpdate(sql);
    }

    @Test
    public void should_return_a_temperature() {
        //given
        String sql = "SELECT * FROM TEMPERATURE WHERE ID = 1";
        //when
        sut.getTemp();
        //then
        verify(dbUtils).executeQuery(sql);
    }
}
