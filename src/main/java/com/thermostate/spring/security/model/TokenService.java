package com.thermostate.spring.security.model;

import com.thermostate.users.domain.User;

public interface TokenService {
    String generateToken(User user);

    LogedInUser parseToken(String token);
}
