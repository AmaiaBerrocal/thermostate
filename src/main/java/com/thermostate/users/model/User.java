package com.thermostate.users.model;

import com.thermostate.shared.ClientError;
import com.thermostate.shared.events.domain.AggregateRoot;
import com.thermostate.users.model.event.UserCreated;
import com.thermostate.users.model.event.UserLoggedIn;
import com.thermostate.users.model.event.UserLoginFailure;
import com.thermostate.users.model.service.HashGenerator;
import lombok.Getter;

import java.util.UUID;

@Getter
public class User extends AggregateRoot {
    private UUID id;
    private final String name;
    private final String password;
    private final String email;
    private final String salt;

    public User(UUID id, String name, String password, String email, String salt) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.salt = salt;
        checkData(name, password, email);
    }

    public User(String name, String password, String email, String salt) {
        checkData(name, password, email);
        this.name = name;
        this.password = password;
        this.email = email;
        this.salt = salt;
    }

    public boolean checkIfAuthenticated(String pass) {
        String loginUserHash = HashGenerator.generate(pass, salt);
        if (loginUserHash.equals(password)) {
            record(new UserLoggedIn(name));
            return true;
        } else {
            record(new UserLoginFailure(name));
            return false;
        }
    }

    public static User with(UUID uuid, String name, String password, String email, String salt) {
        if (null == password) throw ClientError.becauseInvalidDataFromClient();
        return new User(uuid, name, HashGenerator.generate(password, salt), email, salt);
    }

    public void checkData(String name, String password, String email) {
        if (!(isValidName(name)
                && isValidPassword(password)
                && isValidEmail(email))){
            throw ClientError.becauseInvalidDataFromClient();
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

    public void create(UserRepo userRepo) {
        userRepo.create(this);
        this.record(new UserCreated(name));
    }
}
