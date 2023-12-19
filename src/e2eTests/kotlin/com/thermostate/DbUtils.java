package com.thermostate;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DbUtils {

    private Connection createConnection() {
        Connection con = null;
        try{
            con = DriverManager.getConnection("jdbc:sqlite:./assets/thermostate.db");
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
}
