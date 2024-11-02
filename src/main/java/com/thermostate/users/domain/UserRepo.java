package com.thermostate.users.domain;


public interface UserRepo {
    void create(User user);

    User getByName(String name);
}
