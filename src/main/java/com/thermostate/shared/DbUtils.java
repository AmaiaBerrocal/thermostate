package com.thermostate.shared;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class DbUtils {
    final PropertiesLoader properties;

    private Connection createConnection() {
        Connection con = null;
        try{
            con = DriverManager.getConnection(properties.getDbUrl());
            if (con == null){
                throw new RuntimeException("Not able to connect to database.");
            }
            return con;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void executeUpdate(String sql) {
        try(Connection con = createConnection();
            Statement stms = con.createStatement()) {
            stms.executeUpdate(sql);
        } catch (Exception e) {
            throw new RuntimeException("Unable to execute " + sql, e);
        }
    }

    public List<Map<String, Object>> executeQuery(String sql) {
        try(Connection con = createConnection();
            Statement stms = con.createStatement()) {
            ResultSet result = stms.executeQuery(sql);
            return resultToList(result);
        } catch (Exception e) {
            throw new RuntimeException("Error ejecutando la sql: " + sql, e);
        }
    }

    public List<Map<String, Object>> resultToList(ResultSet result) throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        while (result.next()) {
            ResultSetMetaData md = result.getMetaData();
            Map<String, Object> row = new HashMap<>();
            int columns = md.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                row.put(md.getColumnName(i), result.getObject(i));
            }
            resultList.add(row);
        }
        return resultList;
    }
}
