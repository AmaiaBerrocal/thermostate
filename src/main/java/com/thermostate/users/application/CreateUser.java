package com.thermostate.users.application;

import com.thermostate.schedules.model.events.EventBus;
import com.thermostate.shared.ClientError;
import com.thermostate.shared.domain.exceptions.Unauthorized;
import com.thermostate.users.infrastucture.data.UserType;
import com.thermostate.users.model.service.HashGenerator;
import com.thermostate.users.model.service.RandomStringGenerator;
import com.thermostate.users.model.User;
import com.thermostate.users.model.UserRepo;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateUser {
    final UserRepo userRepo;
    final EventBus eventBus;
    final RandomStringGenerator randomStringGenerator;

    public CreateUser(UserRepo userRepo, EventBus eventBus, RandomStringGenerator randomStringGenerator) {
        this.userRepo = userRepo;
        this.eventBus = eventBus;
        this.randomStringGenerator = randomStringGenerator;
    }

    public void execute(UUID uuid, String name, String password, String email, UserType type, UserType currentUserType) {
        if (password == null) throw ClientError.becauseInvalidDataFromClient();
        checkPermissions(currentUserType);
        String salt = randomStringGenerator.generate();
        User user = User.with(uuid, name, HashGenerator.generate(password, salt), email, salt, type, true);
        user.create(userRepo);
        user.publishEventsIn(eventBus);
    }

    private static void checkPermissions(UserType userType) {
        if (!UserType.ADMIN.equals(userType)) {
            throw Unauthorized.becauseNotAbleToCreateUsers(userType);
        }
    }
}
