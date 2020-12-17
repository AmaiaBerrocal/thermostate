package com.thermostate.application;

import com.thermostate.dominio.User;
import com.thermostate.infrastructure.SqulitoConnection;

public class Users {
    SqulitoConnection conn;

    public Users(SqulitoConnection conn) {
        this.conn = conn;
    }

    public boolean isValidPassword(String name, String password) {
        User user = conn.findUserByUsername(name);
        if (user == null) {
            return false;
        } else {
            return (user.getPassword().equals(password));
        }
    }
}
