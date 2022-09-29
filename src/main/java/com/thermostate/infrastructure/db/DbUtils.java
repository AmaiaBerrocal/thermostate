package com.thermostate.infrastructure.db;

import com.thermostate.infrastructure.properties.PropertiesLoader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@AllArgsConstructor
public class DbUtils {
    final PropertiesLoader properties;

    private Connection createConnection() {
        Connection con = null;
        try{
            con = DriverManager.getConnection(properties.getUrl());
            if (con == null){
                throw new RuntimeException("Not able to connect to database.");
            }
            return con;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void executeUpdate(String sql) throws SQLException {
        try(Connection con = createConnection();
            Statement stms = con.createStatement()) {
            stms.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
