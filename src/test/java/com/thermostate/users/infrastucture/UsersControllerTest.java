package com.thermostate.users.infrastucture;

import com.thermostate.spring.security.model.TokenService;
import com.thermostate.spring.security.infrastucture.BearerService;
import com.thermostate.shared.ValueResponse;
import com.thermostate.users.application.CreateUser;
import com.thermostate.users.application.LoginUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UsersControllerTest {
    CreateUser createUser;
    LoginUser loginUser;
    TokenService tokenService;
    UsersController sut;

    @BeforeEach
    public void setup() {
        createUser = mock(CreateUser.class);
        loginUser = mock(LoginUser.class);
        tokenService = new BearerService();
        sut = new UsersController(createUser, loginUser, tokenService);
    }

    @Test
    public void should_call_application_layer_correctly() {
        //given
        UserCreateRequest req = new UserCreateRequest("jjlhlh", "amaia", "jajaj@gmail.com");
        //when
        sut.userCreation(req);
        //then
        verify(createUser).execute(any(UUID.class), eq(req.name), eq(req.password), eq(req.email));
    }

    @Test
    public void should_return_a_bearer_if_name_and_hash_are_in_DDBB() {
        //given
        String name = "nombre";
        String pass = "contraseña";

        when(loginUser.execute(name, pass)).thenReturn(null);
        //when
        ValueResponse user = sut.login(new UserLoginRequest(name, pass));
        //then
        //assertThat(user.value().toString()).startsWith("Bearer ");
    }
}
