package com.thermostate.users.infrastucture;

import com.thermostate.shared.PropertiesLoader;
import com.thermostate.users.application.CreateUser;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class UsersController {
    final CreateUser createUser;
    final PropertiesLoader properties;
    final UserDbRepo userUtils;

    public UsersController(CreateUser createUser, PropertiesLoader properties, UserDbRepo userUtils) {
        this.createUser = createUser;
        this.properties = properties;
        this.userUtils = userUtils;
    }

    @PostMapping("user")
    public void createUser(@RequestBody UserCreateRequest userCreateRequest) throws SQLException {
        createUser.execute(userCreateRequest.name, userCreateRequest.password, userCreateRequest.email);
    }
}

@AllArgsConstructor
class UserCreateRequest {

    String password;
    String name;
    String email;
}