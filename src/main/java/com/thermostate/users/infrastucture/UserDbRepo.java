package com.thermostate.users.infrastucture;

import com.thermostate.shared.DbUtils;
import com.thermostate.users.model.User;
import com.thermostate.users.model.UserRepo;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class UserDbRepo implements UserRepo {
    final DbUtils dbUtils;

    public UserDbRepo(DbUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERS (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT UNIQUE NOT NULL," +
                "PASSWORD VARCHAR NOT NULL," +
                "SALT TEXT NOT NULL," +
                "EMAIL TEXT UNIQUE NOT NULL," +
                "CREATED_AT DATE," +
                "ACTIVE BOOLEAN);";
        dbUtils.executeUpdate(sql);
    }

    @Override
    public void create(User user) {
        String sql = "INSERT INTO USERS (NAME, PASSWORD, EMAIL, SALT, CREATED_AT, ACTIVE) VALUES (" +
                "'" + user.name() +
                "','" + user.password() +
                "','" + user.email() +
                "','" + user.salt() +
                "', CURRENT_TIMESTAMP," +
                " true)";

        dbUtils.executeUpdate(sql);
    }
}
