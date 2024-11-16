package com.thermostate.users.domain;

import com.thermostate.shared.ClientError;
import com.thermostate.shared.events.domain.AggregateRoot;
import com.thermostate.users.infrastucture.data.UserRole;
import com.thermostate.users.domain.event.UserCreated;
import com.thermostate.users.domain.event.UserLoggedIn;
import com.thermostate.users.domain.event.UserLoginFailure;
import com.thermostate.users.domain.service.HashGenerator;
import lombok.Getter;

import java.util.UUID;

@Getter
public class User extends AggregateRoot {
    private UUID id;
    private final String name;
    private final String password;
    private final String email;
    private final String salt;
    private final UserRole role;
    private final Boolean isActive;

    private User(UUID id, String name, String password, String email, String salt, UserRole role, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.salt = salt;
        this.role = role;
        this.isActive = isActive;
        checkData(name, password, email);
    }

    public void create(UserRepo userRepo) {
        userRepo.create(this);
        this.record(new UserCreated(name));
    }

    public boolean checkIfAuthenticated(String pass) {
        String loginUserHash = HashGenerator.generateHashedPassword(pass, salt);
        if (loginUserHash.equals(password)) {
            record(new UserLoggedIn(name));
            return true;
        } else {
            record(new UserLoginFailure(name));
            return false;
        }
    }

    public static User with(UUID uuid,
                            String name,
                            String hashedPassword,
                            String email,
                            String salt,
                            UserRole type,
                            Boolean isActive) {
        if (null == hashedPassword) throw ClientError.becauseInvalidDataFromClient();
        return new User(uuid, name, hashedPassword, email, salt, type, isActive);
    }

    public static User with(UUID uuid,
                            String name,
                            String password,
                            String email,
                            UserRole type,
                            Boolean isActive) {
        String salt = HashGenerator.generateRandomString();
        return User.with(uuid, name, HashGenerator.generateHashedPassword(password, salt), email, salt, type, isActive);
    }

    private void checkData(String name, String password, String email) {
        if (!(isValidName(name)
                && isValidPassword(password)
                && isValidEmail(email))){
            throw ClientError.becauseInvalidDataFromClient();
        }
    }

    private boolean isValidName(String name) {
        return isNotEmpty(name);
    }

    private boolean isValidPassword(String password) {
        return isNotEmpty(password);
    }

    private boolean isValidEmail(String email) {
        return isNotEmpty(email) && email.contains("@");
    }

    private static boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
