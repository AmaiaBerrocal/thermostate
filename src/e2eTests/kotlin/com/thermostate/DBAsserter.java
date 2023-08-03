package com.thermostate;


import java.util.Map;

class DBAsserter {

    DbUtils dbUtils = new DbUtils();

    Map<String, String> tableGenerationSqls = Map.of(
        "USERS","""
            CREATE TABLE IF NOT EXISTS USERS (ID INTEGER PRIMARY KEY AUTOINCREMENT,
            NAME TEXT UNIQUE NOT NULL,
            PASSWORD VARCHAR NOT NULL,
            SALT TEXT NOT NULL,
            EMAIL TEXT UNIQUE NOT NULL,
            CREATED_AT DATE,
            ACTIVE BOOLEAN);
            """,
        "SCHEDULES", """
            CREATE TABLE IF NOT EXISTS SCHEDULES (ID INTEGER PRIMARY KEY AUTOINCREMENT,
            WEEKDAYS TEXT NOT NULL,
            TIME_FROM TEXT NOT NULL,
            TIME_TO TEXT NOT NULL,
            ACTIVE BOOLEAN,
            MIN_TEMP INTEGER,
            CREATED_AT DATE);
        """,
        "TEMPERATURE", """
             CREATE TABLE IF NOT EXISTS TEMPERATURE (
             ID INTEGER NOT NULL,
             TEMP INTEGER NOT NULL);
        """
    );

    public void assertTableExist(String table) {
        try {
            selectAllFrom(table);
        } catch (Exception e) {
            dbUtils.executeUpdate(tableGenerationSqls.get(table));
        }
    }

    private void selectAllFrom(String table) {
        dbUtils.executeQuery("Select * from " + table);
    }

    public void update(String sql) {
        dbUtils.executeUpdate(sql);
    }

}