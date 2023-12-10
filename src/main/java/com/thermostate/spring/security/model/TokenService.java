package com.thermostate.spring.security.model;

import com.thermostate.users.model.User;

public interface TokenService {
    String generateToken(User user);

    LogedInUser parseToken(String token);
}
