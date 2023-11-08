package com.thermostate.temperature.infrastructure;

import com.google.common.eventbus.EventBus;
import com.thermostate.shared.DbUtils;
import com.thermostate.temperature.model.Temperature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.events.EventTarget;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TemperatureDbRepoTest {
    DbUtils dbUtils;
    TemperatureDbRepo sut;
    EventBus eventBus;

    @BeforeEach
    public void setup() {
        dbUtils = mock(DbUtils.class);
        eventBus = mock(EventBus.class);
        sut = new TemperatureDbRepo(dbUtils, eventBus);
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
        String sql = "SELECT * FROM TEMPERATURE";
        when(dbUtils.executeQuery(any())).
                thenReturn(List.of(Map.of("TEMP", 42)));
        //when
        Temperature temp = sut.getTemp();
        //then
        verify(dbUtils).executeQuery(sql);
        assertThat(temp.getTemp()).isEqualTo(42);
    }

    @Test
    public void should_update_a_temperature() {
        //given
        Temperature temperature = new Temperature(42);
        String sql = "UPDATE TEMPERATURE SET TEMP = " + temperature.getTemp();
        //when
        sut.updateTemp(temperature);
        //then
        verify(dbUtils).executeUpdate(sql);
    }
}
