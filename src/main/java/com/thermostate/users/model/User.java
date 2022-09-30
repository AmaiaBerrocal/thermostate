package com.thermostate.users.model;

public record User(String name, String password, String email, String salt) {}
