package com.thermostate.users.infrastucture;

import com.thermostate.security.application.TokenService;
import com.thermostate.security.infrastucture.BearerService;
import com.thermostate.shared.ValueResponse;
import com.thermostate.users.application.CreateUser;
import com.thermostate.users.application.GetUser;
import com.thermostate.users.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.thermostate.users.model.UserObjectMother.randomUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UsersControllerTest {
    CreateUser createUser;
    GetUser getUser;
    TokenService tokenService;
    UsersController sut;

    @BeforeEach
    public void setup() {
        createUser = mock(CreateUser.class);
        getUser = mock(GetUser.class);
        tokenService = new BearerService();
        sut = new UsersController(createUser, getUser, tokenService);
    }

    @Test
    public void should_call_application_layer_correctly() {
        //given
        UserCreateRequest req = new UserCreateRequest("jjlhlh", "amaia", "jajaj@gmail.com");
        //when
        sut.userCreation(req);
        //then
        verify(createUser).execute(req.name, req.password, req.email);
    }

    @Test
    public void should_return_an_User_if_name_and_hash_are_in_DDBB() {
        //given
        User expected = randomUser();
        String name = "nombre";
        String pass = "contraseña";
        when(getUser.execute(name, pass)).thenReturn(expected);
        //when
        ValueResponse user = sut.login(new UserLoginRequest(name, pass));
        //then
        assertThat(user.value()).startsWith("Bearer ");
    }
}
