package com.thermostate.users.infrastucture;

import com.thermostate.spring.security.model.LogedInUser;
import com.thermostate.spring.security.model.TokenService;
import com.thermostate.shared.ClientError;
import com.thermostate.shared.ValueResponse;
import com.thermostate.users.application.CreateUser;
import com.thermostate.users.application.LoginUser;
import com.thermostate.users.domain.LogedUserInfo;
import com.thermostate.users.infrastucture.data.UserRole;
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
                UserRole.valueOf(userCreateRequest.userRole),
                loginUser.userRole()
        );
    }

    @PostMapping("/login")
    @ResponseBody
    public ValueResponse<LogedUserInfo> login(@RequestBody UserLoginRequest request) {
        LogedUserInfo userInfo =  loginUser.execute(request.name, request.password);
        return new ValueResponse<>(userInfo);
    }

    @ExceptionHandler(value = ClientError.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unable to connect")
    public void handleException(Exception ex) {
        ex.printStackTrace();
    }

}

@AllArgsConstructor
class UserCreateRequest {

    String userRole;
    String password;
    String name;
    String email;
}

@AllArgsConstructor
class UserLoginRequest {
    String name;
    String password;
}
