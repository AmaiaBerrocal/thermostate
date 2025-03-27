package com.thermostate.users.application;

import com.thermostate.schedules.domain.events.EventBus;
import com.thermostate.spring.security.model.TokenService;
import com.thermostate.shared.ClientError;
import com.thermostate.users.domain.LogedUserInfo;
import com.thermostate.users.domain.User;
import com.thermostate.users.domain.UserRepo;
import org.springframework.stereotype.Component;

@Component
public class LoginUser {
    public final UserRepo userRepo;
    public final EventBus eventBus;
    public final TokenService tokenService;

    public LoginUser(UserRepo userRepo, EventBus eventBus, TokenService tokenService) {
        this.userRepo = userRepo;
        this.eventBus = eventBus;
        this.tokenService = tokenService;
    }

    public LogedUserInfo execute(String name, String password) {
        User loginUser = userRepo.getByName(name);
        if (loginUser == null) {
            throw ClientError.becauseInvalidDataFromClient();
        }
        return LogedUserInfo.from(loginUser.getId(), createBearer(password, loginUser), loginUser.getRole());
    }

    private String createBearer(String password, User loginUser) {
        try {
            if (loginUser.checkIfAuthenticated(password)) {
                if (!loginUser.getIsActive()) {
                    throw ClientError.becauseDeactivatedUser(loginUser);
                }
                return tokenService.generateToken(loginUser);
            } else {
                throw ClientError.becauseInvalidDataFromClient();
            }
        } finally {
            loginUser.publishEventsIn(eventBus);
        }
    }
}
