package com.thermostate.users.application;

import com.thermostate.shared.ClientError;
import com.thermostate.shared.HashGenerator;
import com.thermostate.shared.RandomStringGenerator;
import com.thermostate.users.model.User;
import com.thermostate.users.model.UserRepo;
import org.springframework.stereotype.Component;

@Component
public class CreateUser {
    final UserRepo userRepo;
    final RandomStringGenerator randomStringGenerator;
    final HashGenerator hashGenerator;

    public CreateUser(UserRepo userRepo, RandomStringGenerator randomStringGenerator, HashGenerator hashGenerator) {
        this.userRepo = userRepo;
        this.randomStringGenerator = randomStringGenerator;
        this.hashGenerator = hashGenerator;
    }

    public void execute(String name, String password, String email) {
        checkData(name, password, email);
        String salt = randomStringGenerator.generate();
        String hash = hashGenerator.generate(password, salt);
        User user = new User(-1, name, hash, email, salt);
        userRepo.create(user);
    }

    private void checkData(String name, String password, String email) {
        if (!(isValidName(name)
                && isValidPassword(password)
                && isValidEmail(email))){
            throw (new ClientError());
        }
    }

    public boolean isValidName(String name) {
        return isNotEmpty(name);
    }

    public boolean isValidPassword(String password) {
        return isNotEmpty(password);
    }

    public boolean isValidEmail(String email) {
        return isNotEmpty(email) && email.contains("@");
    }

    private static boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
