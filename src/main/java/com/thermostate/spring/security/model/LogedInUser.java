package com.thermostate.spring.security.model;

import com.thermostate.users.infrastucture.data.UserRole;

import java.util.UUID;

public record LogedInUser(String name, String email, UUID id, UserRole userRole) {
}
