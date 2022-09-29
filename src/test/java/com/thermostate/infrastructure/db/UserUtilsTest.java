package com.thermostate.infrastructure.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserUtilsTest {

    DbUtils dbUtils;

    @BeforeEach
    public void setup() {
        dbUtils = mock(DbUtils.class);
    }

    @Test
    public void users_table_should_be_create() throws SQLException {
        //given
        UserUtils sut = new UserUtils(dbUtils);
        //when
        sut.createUserTable();
        //then
        verify(dbUtils).executeUpdate(sql);
    }

    String sql = "CREATE TABLE IF NOT EXISTS USERS (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "NAME TEXT NOT NULL," +
            "PASSWORD VARCHAR NOT NULL," +
            "SALT TEXT NOT NULL," +
            "MAIL TEXT NOT NULL," +
            "CREATE_AT DATE," +
            "ACTIVE BOOLEAN);";
}