package com.thermostate.security.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LogedInUser {
    public final String name;
    public final String email;
    public final Integer id;
}
