package com.thermostate.infrastructure.db;

import java.sql.SQLException;

public class UserUtils {
    final DbUtils dbUtils;

    public UserUtils(DbUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public void createUserTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS USERS (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT NOT NULL," +
                "PASSWORD VARCHAR NOT NULL," +
                "SALT TEXT NOT NULL," +
                "MAIL TEXT NOT NULL," +
                "CREATE_AT DATE," +
                "ACTIVE BOOLEAN);";
        dbUtils.executeUpdate(sql);
    }
}
