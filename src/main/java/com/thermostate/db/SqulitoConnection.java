package com.thermostate.db;

import java.sql.*;

public class SqulitoConnection {
    public static void main( String args[] ) {
        createTable();
        insertUser("AMAIA", "OLIHFWOE");
        insertUser("INIGO", "LKONSEDJNFCD");
    }

    public static Connection createConnection() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:squlito/test.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Opened database successfully");
        return c;
    }

    private static void createTable() {
        try(Connection c = createConnection();
            Statement stmt = c.createStatement()) {

            String sql = "CREATE TABLE USERS" +
                    "(ID INTEGER PRIMARY KEY," +
                    " NAME TEXT NOT NULL, " +
                    " PASSWORD TEXT NOT NULL, " +
                    " SALT TEXT)";
            stmt.executeUpdate(sql);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Table created successfully");
    }

    public static void insertUser(String name, String password) {
        try(Connection c = createConnection();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO USERS (NAME,PASSWORD,SALT) VALUES (?, ?, null);")) {
            stmt.setString(1, name);
            stmt.setString(2, password);
            stmt.executeUpdate();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }
}