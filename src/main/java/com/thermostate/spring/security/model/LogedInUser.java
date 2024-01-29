package com.thermostate.spring.security.model;

import java.util.UUID;

public record LogedInUser(String name, String email, UUID id) {
}
