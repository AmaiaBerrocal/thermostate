package com.thermostate.security.model;

import com.thermostate.security.model.LogedInUser;
import com.thermostate.users.model.User;

public interface TokenService {
    String generateToken(User user);

    LogedInUser parseToken(String token);
}
