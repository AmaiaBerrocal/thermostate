package com.thermostate.users.infrastucture;

import com.thermostate.security.application.TokenService;
import com.thermostate.shared.ClientError;
import com.thermostate.shared.ValueResponse;
import com.thermostate.users.application.CreateUser;
import com.thermostate.users.application.GetUser;
import com.thermostate.users.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
public class UsersController {
    private final CreateUser createUser;
    private final GetUser getUser;
    private final TokenService tokenService;


    public UsersController(CreateUser createUser,
                           GetUser getUser,
                           TokenService tokenService) {
        this.createUser = createUser;
        this.getUser = getUser;
        this.tokenService = tokenService;
    }

    @PostMapping("/user")
    public void userCreation(@RequestBody UserCreateRequest userCreateRequest) {
        createUser.execute(userCreateRequest.name,
                userCreateRequest.password,
                userCreateRequest.email);
    }

    @ExceptionHandler(value = ClientError.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleException(Exception ex) {
        ex.printStackTrace();
    }

    @PostMapping("/login")
    @ResponseBody
    public ValueResponse<String> login(@RequestBody UserLoginRequest request) {
        User user =  getUser.execute(request.name, request.password);
        return new ValueResponse<>(tokenService.generateToken(user));
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