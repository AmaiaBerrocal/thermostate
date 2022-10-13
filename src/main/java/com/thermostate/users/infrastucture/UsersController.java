package com.thermostate.users.infrastucture;

import com.thermostate.shared.ClientError;
import com.thermostate.shared.PropertiesLoader;
import com.thermostate.users.application.CreateUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class UsersController {
    private final CreateUser createUser;


    public UsersController(CreateUser createUser) {
        this.createUser = createUser;
    }

    @PostMapping("/user")
    public void userCreation(@RequestBody UserCreateRequest userCreateRequest) {
        createUser.execute(userCreateRequest.name, userCreateRequest.password, userCreateRequest.email);
    }

    @ExceptionHandler(value = ClientError.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleException(Exception ex) {
    }
}

@AllArgsConstructor
class UserCreateRequest {

    String password;
    String name;
    String email;
}