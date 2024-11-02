package com.thermostate.users.infrastucture;

import com.thermostate.spring.security.model.LogedInUser;
import com.thermostate.spring.security.model.TokenService;
import com.thermostate.shared.ClientError;
import com.thermostate.shared.ValueResponse;
import com.thermostate.users.application.CreateUser;
import com.thermostate.users.application.LoginUser;
import com.thermostate.users.infrastucture.data.UserType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin()
public class UsersController {
    private final CreateUser createUser;
    private final LoginUser loginUser;
    private final TokenService tokenService;


    public UsersController(CreateUser createUser,
                           LoginUser loginUser,
                           TokenService tokenService) {
        this.createUser = createUser;
        this.loginUser = loginUser;
        this.tokenService = tokenService;
    }

    @PostMapping("/user")
    public void userCreation(@RequestBody UserCreateRequest userCreateRequest) {
        LogedInUser loginUser = (LogedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        createUser.execute(
                UUID.randomUUID(),
                userCreateRequest.name,
                userCreateRequest.password,
                userCreateRequest.email,
                UserType.valueOf(userCreateRequest.userType),
                loginUser.userType()
        );
    }

    @PostMapping("/login")
    @ResponseBody
    public ValueResponse<String> login(@RequestBody UserLoginRequest request) {
        String bearer =  loginUser.execute(request.name, request.password);
        return new ValueResponse<>(bearer);
    }

    @ExceptionHandler(value = ClientError.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unable to connect")
    public void handleException(Exception ex) {
        ex.printStackTrace();
    }

}

@AllArgsConstructor
class UserCreateRequest {

    String userType;
    String password;
    String name;
    String email;
}

@AllArgsConstructor
class UserLoginRequest {
    String name;
    String password;
}
