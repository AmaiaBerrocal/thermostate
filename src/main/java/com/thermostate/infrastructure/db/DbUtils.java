package com.thermostate.infrastructure.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DbUtils {
    //url siempre es jdbc:subprotocol:subname donde subname es la ruta y nombre de mi archivo
    final String URL = "jdbc:sqlite:./db/thermostate.db";

    private Connection createConnection() {
        Connection con = null;
        try{
            con = DriverManager.getConnection(URL);
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
