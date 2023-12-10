package com.thermostate.users.application;

import com.thermostate.spring.security.model.TokenService;
import com.thermostate.shared.ClientError;
import com.thermostate.users.model.User;
import com.thermostate.users.model.UserRepo;
import org.springframework.stereotype.Component;

@Component
public class LoginUser {
    final UserRepo userRepo;
    final EventBus eventBus;
    final TokenService tokenService;

    public LoginUser(UserRepo userRepo, EventBus eventBus, TokenService tokenService) {
        this.userRepo = userRepo;
        this.eventBus = eventBus;
        this.tokenService = tokenService;
    }

    public String execute(String name, String password) {
        User loginUser = userRepo.getByName(name);
        if (loginUser == null) {
            throw ClientError.becauseInvalidDataFromClient();
        }
        return createBearer(password, loginUser);
    }

    private String createBearer(String password, User loginUser) {
        try {
            if (loginUser.checkIfAuthenticated(password)) {
                return tokenService.generateToken(loginUser);
            } else {
                throw ClientError.becauseInvalidDataFromClient();
            }
        } finally {
            loginUser.publishEventsIn(eventBus);
        }
    }
}
