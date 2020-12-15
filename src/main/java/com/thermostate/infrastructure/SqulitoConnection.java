package com.thermostate.infrastructure;

import com.thermostate.dominio.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqulitoConnection {
    public static void main(String args[]) {
        //createTable();
        //insertUser(new User("amarillo", "colorao", ""));
        //User user = findUserByUsername("ROSA");
        //User user = findUserById(2);
        boolean isValid = isValidPassword("ROSA", "colorao");
        System.out.println(isValid);
    }

    public static Connection createConnection() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:squlito/test.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Opened database successfully");
        return c;
    }

    private static void createTable() {
        try(Connection c = createConnection();
            Statement stmt = c.createStatement()) {

            String sql = "CREATE TABLE USERS" +
                    "(ID INTEGER PRIMARY KEY," +
                    " NAME TEXT NOT NULL UNIQUE, " +
                    " PASSWORD TEXT NOT NULL, " +
                    " SALT TEXT)";
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Table created successfully");
    }

    public static void insertUser(User user) {
        try(Connection c = createConnection();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO USERS (NAME,PASSWORD,SALT) VALUES (?, ?, ?);")) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getSalt());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public static User findUserByUsername(String name) {
        User user = null;
        try(Connection c = createConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM USERS WHERE NAME=?;")) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("SALT")
                        );
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return user;
    }

    public static User findUserById(int id) {
        User user = null;
        try(Connection c = createConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM USERS WHERE ID=?;")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("SALT")
                );
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return user;
    }

    public static boolean isValidPassword(String name, String password) {
        User user = findUserByUsername(name);
        return (user.getPassword().equals(password));
    }
}