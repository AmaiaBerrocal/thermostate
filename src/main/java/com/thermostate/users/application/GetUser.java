package com.thermostate.users.application;

import com.google.common.eventbus.EventBus;
import com.thermostate.shared.HashGenerator;
import com.thermostate.users.model.User;
import com.thermostate.users.model.UserRepo;
import org.springframework.stereotype.Component;

@Component
public class GetUser {
    final UserRepo userRepo;
    final HashGenerator hashGenerator;
    final EventBus eventBus;

    public GetUser(UserRepo userRepo, HashGenerator hashGenerator, EventBus eventBus) {
        this.userRepo = userRepo;
        this.hashGenerator = hashGenerator;
        this.eventBus = eventBus;
    }

    public User execute(String name, String password) {
        User loginUser = userRepo.getByName(name);
        if (loginUser == null) {
            return null;
        }
        String loginUserHash = hashGenerator.generate(password,
                loginUser.salt());
        String dbUserHash = loginUser.password();
        if (loginUserHash.equals(dbUserHash)) {
            System.out.println(Thread.currentThread().getId() + " from app");
            eventBus.post(loginUser.name());
            return loginUser;
        } else {
            return null;
        }
    }
}
