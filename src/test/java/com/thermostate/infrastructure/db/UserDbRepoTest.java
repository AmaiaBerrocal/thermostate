package com.thermostate.infrastructure.db;

import com.thermostate.shared.DbUtils;
import com.thermostate.users.infrastucture.UserDbRepo;
import com.thermostate.users.model.User;
import com.thermostate.users.model.UserObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserDbRepoTest {

    DbUtils dbUtils;

    @BeforeEach
    public void setup() {
        dbUtils = mock(DbUtils.class);
    }

    @Test
    public void users_table_should_be_create() throws SQLException {
        //given
        UserDbRepo sut = new UserDbRepo(dbUtils);
        //when
        sut.createUserTable();
        //then
        verify(dbUtils).executeUpdate(sql);
    }

    @Test
    public void user_should_be_insert() {
        //given
        UserDbRepo sut = new UserDbRepo(dbUtils);
        User user = UserObjectMother.randomUser();
        String sql = "INSERT INTO USERS (NAME, PASSWORD, EMAIL, SALT, CREATED_AT, ACTIVE) VALUES (" +
                "'" + user.name() +
                "','" + user.password() +
                "','" + user.email() +
                "','" + user.salt() +
                "', CURRENT_TIMESTAMP," +
                " true)";
        //when
        sut.create(user);
        //then
        verify(dbUtils).executeUpdate(sql);
    }

    String sql = "CREATE TABLE IF NOT EXISTS USERS (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "NAME TEXT UNIQUE NOT NULL," +
            "PASSWORD VARCHAR NOT NULL," +
            "SALT TEXT NOT NULL," +
            "EMAIL TEXT UNIQUE NOT NULL," +
            "CREATED_AT DATE," +
            "ACTIVE BOOLEAN);";
}
