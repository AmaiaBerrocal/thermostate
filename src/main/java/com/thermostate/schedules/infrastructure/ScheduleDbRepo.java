package com.thermostate.schedules.infrastructure;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleRepo;
import com.thermostate.shared.DbUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Component
public class ScheduleDbRepo implements ScheduleRepo {
    final DbUtils dbUtils;

    public ScheduleDbRepo(DbUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public void createScheduleTable() {
        String sql = "CREATE TABLE IF NOT EXISTS SCHEDULES (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "WEEKDAYS TEXT NOT NULL," +
                "TIME_FROM TEXT NOT NULL," +
                "TIME_TO TEXT NOT NULL," +
                "ACTIVE BOOLEAN," +
                "MIN_TEMP INTEGER," +
                "CREATED_AT DATE);";
        dbUtils.executeUpdate(sql);
    }

    @Override
    public void create(Schedule schedule) {
        String sql = "INSERT INTO SCHEDULES (WEEKDAYS, TIME_FROM, TIME_TO, ACTIVE, MIN_TEMP, CREATED_AT) VALUES (" +
                "'" + schedule.weekDays +
                "','" + schedule.timeFrom +
                "','" + schedule.timeTo +
                "', 1" +
                "," + schedule.minTemp +
                ", CURRENT_DATE)";
        dbUtils.executeUpdate(sql);
    }

    @Override
    public Schedule getById(Integer id) {
        String sql = "SELECT * FROM SCHEDULES WHERE ID = " + id;
        List<Map<String, Object>> result = dbUtils.executeQuery(sql);
        if (result.isEmpty()) {
            return null;
        }
        Map<String, Object> res = result.get(0);
        return buildScheduleFromMap(res);
    }


    @Override
    public void update(Schedule schedule) {
        String sql = "UPDATE SCHEDULES SET " +
                "WEEKDAYS = '" + schedule.weekDays + "', " +
                "TIME_FROM = '" + schedule.timeFrom + "', " +
                "TIME_TO = '" + schedule.timeTo + "', " +
                "ACTIVE = '" + (schedule.active ? 1 : 0) + "', " +
                "MIN_TEMP = " + schedule.minTemp + " " +
                "WHERE ID = " + schedule.id;

        System.out.println(sql);
        dbUtils.executeUpdate(sql);
    }

    @NotNull
    private static Schedule buildScheduleFromMap(Map<String, Object> row) {
        System.out.println(row);
        return new Schedule((Integer) row.get("ID"),
                (String) row.get("WEEKDAYS"),
                (String) row.get("TIME_FROM"),
                (String) row.get("TIME_TO"),
                ((Integer) row.get("ACTIVE")) == 1,
                (Integer) row.get("MIN_TEMP"),
                LocalDate.parse(row.get("CREATED_AT").toString().trim()));
    }

    @Override
    public List<Schedule> getAll() {
        String sql = "SELECT * FROM SCHEDULES";
        List<Map<String, Object>> result = dbUtils.executeQuery(sql);
        if (result.isEmpty()) {
            return null;
        }

        return result.stream().map(ScheduleDbRepo::buildScheduleFromMap).toList();
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM SCHEDULES WHERE ID='" + id + "'";
        dbUtils.executeUpdate(sql);
    }
}
