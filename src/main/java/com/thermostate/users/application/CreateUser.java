package com.thermostate.users.application;

import com.thermostate.schedules.domain.events.EventBus;
import com.thermostate.shared.ClientError;
import com.thermostate.shared.domain.exceptions.Unauthorized;
import com.thermostate.users.infrastucture.data.UserRole;
import com.thermostate.users.domain.User;
import com.thermostate.users.domain.UserRepo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.UUID;

@Component
@CrossOrigin()
public class CreateUser {
    final UserRepo userRepo;
    final EventBus eventBus;

    public CreateUser(UserRepo userRepo, EventBus eventBus) {
        this.userRepo = userRepo;
        this.eventBus = eventBus;
    }

    public void execute(UUID uuid, String name, String password, String email, UserRole type, UserRole currentUserRole) {
        if (password == null) throw ClientError.becauseInvalidDataFromClient();
        checkPermissions(currentUserRole);
        User user = User.with(uuid, name, password, email, type, true);
        user.create(userRepo);
        user.publishEventsIn(eventBus);
    }

    private static void checkPermissions(UserRole userRole) {
        if (!UserRole.ADMIN.equals(userRole)) {
            throw Unauthorized.becauseNotAbleToCreateUsers(userRole);
        }
    }
}
