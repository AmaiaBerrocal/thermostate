package com.thermostate.users.application;

import com.thermostate.schedules.model.events.EventBus;
import com.thermostate.shared.ClientError;
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

    public void execute(UUID uuid, String name, String password, String email) {
        if (password == null) throw ClientError.becauseInvalidDataFromClient();
        String salt = randomStringGenerator.generate();
        User user = User.with(uuid, name, HashGenerator.generate(password, salt), email, salt);
        user.create(userRepo);
        user.publishEventsIn(eventBus);
    }
}
