package com.thermostate.schedules.infrastructure;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleRepo;
import com.thermostate.shared.DbUtils;
import org.springframework.stereotype.Component;


@Component
public class ScheduleDbRepo implements ScheduleRepo {
    final DbUtils dbUtils;

    public ScheduleDbRepo(DbUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public void createScheduleTable() {
        String sql = "CREATE TABLE IF NOT EXISTS SCHEDULES (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DATE_FROM DATE NOT NULL," +
                "DATE_TO DATE NOT NULL," +
                "TIME_FROM TEXT NOT NULL," +
                "TIME_TO TEXT NOT NULL," +
                "ACTIVE BOOLEAN," +
                "MIN_TEMP INTEGER," +
                "CREATED_AT DATE);";
        dbUtils.executeUpdate(sql);
    }

    @Override
    public void create(Schedule schedule) {
        String sql = "INSERT INTO SCHEDULES (DATE_FROM, DATE_TO, TIME_FROM, TIME_TO, ACTIVE, MIN_TEMP, CREATED_AT) VALUES (" +
                "'" + schedule.dateFrom() +
                "','" + schedule.dateTo() +
                "','" + schedule.timeFrom() +
                "','" + schedule.timeTo() +
                "',true" +
                "," + schedule.minTemp() +
                ", CURRENT_TIMESTAMP)";
        dbUtils.executeUpdate(sql);
    }
}
