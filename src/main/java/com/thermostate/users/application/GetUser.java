package com.thermostate.users.application;

import com.thermostate.shared.HashGenerator;
import com.thermostate.users.model.User;
import com.thermostate.users.model.UserRepo;
import org.springframework.stereotype.Component;

@Component
public class GetUser {
    final UserRepo userRepo;
    final HashGenerator hashGenerator;

    public GetUser(UserRepo userRepo, HashGenerator hashGenerator) {
        this.userRepo = userRepo;
        this.hashGenerator = hashGenerator;
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
            return loginUser;
        } else {
            return null;
        }
    }
}
