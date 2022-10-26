package com.thermostate.users.infrastucture;

import com.thermostate.shared.ClientError;
import com.thermostate.users.application.CreateUser;
import com.thermostate.users.application.GetUser;
import com.thermostate.users.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {
    private final CreateUser createUser;
    private final GetUser getUser;


    public UsersController(CreateUser createUser, GetUser getUser) {
        this.createUser = createUser;
        this.getUser = getUser;
    }

    @PostMapping("/user")
    public void userCreation(@RequestBody UserCreateRequest userCreateRequest) {
        createUser.execute(userCreateRequest.name, userCreateRequest.password, userCreateRequest.email);
    }

    @ExceptionHandler(value = ClientError.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleException(Exception ex) {
        ex.printStackTrace();
    }

    @PostMapping("/login")
    @ResponseBody
    public User login(@RequestBody UserLoginRequest request) {
        return getUser.execute(request.name, request.password);
    }
}

@AllArgsConstructor
class UserCreateRequest {

    String password;
    String name;
    String email;
}

@AllArgsConstructor
class UserLoginRequest {
    String name;
    String password;
}