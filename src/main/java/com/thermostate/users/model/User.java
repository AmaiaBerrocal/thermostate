package com.thermostate.users.model;

import com.thermostate.shared.HashGenerator;
import com.thermostate.shared.events.AggregateRoot;
import com.thermostate.users.model.event.UserLoggedIn;
import com.thermostate.users.model.event.UserLoginFailure;
import lombok.Getter;

@Getter
public class User extends AggregateRoot {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String salt;

    public User(Integer id, String name, String password, String email, String salt) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.salt = salt;
    }
}
