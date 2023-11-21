package com.thermostate.temperature.infrastructure;

import com.thermostate.shared.DbUtils;
import com.thermostate.temperature.model.Temperature;
import com.thermostate.temperature.model.TemperatureRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TemperatureDbRepo implements TemperatureRepo {
    final DbUtils dbUtils;

    public TemperatureDbRepo(DbUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public void createTemperatureTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TEMPERATURE (" +
                "ID INTEGER NOT NULL," +
                "TEMP INTEGER NOT NULL);";
        sql += "INSERT INTO TEMPERATURE (ID, TEMP) VALUES (" + 1 + ", " + (16 * 100) + ")";
        dbUtils.executeUpdate(sql);
    }

    @Override
    public Temperature getTemp() {
        String sql = "SELECT * FROM TEMPERATURE";
        List<Map<String, Object>> result = dbUtils.executeQuery(sql);
        Map<String, Object> res = result.get(0);
        return buildTemperatureFromMap(res);
    }

    @NotNull
    private static Temperature buildTemperatureFromMap(Map<String, Object> row) {
        return new Temperature((Integer) row.get("TEMP"));
    }

    @Override
    public void updateTemp(Temperature temperature) {
        String sql = "UPDATE TEMPERATURE SET " +
                "TEMP = " + temperature.getTemp();
        dbUtils.executeUpdate(sql);
    }
}
