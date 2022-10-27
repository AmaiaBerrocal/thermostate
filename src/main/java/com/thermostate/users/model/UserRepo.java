package com.thermostate.users.model;


public interface UserRepo {
    void create(User user);

    User getByName(String name);
    //update
    //delete
}
