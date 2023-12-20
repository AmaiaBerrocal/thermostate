package com.thermostate.spring.security.model;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class LogedInUser {
    public final String name;
    public final String email;
    public final UUID id;
}
