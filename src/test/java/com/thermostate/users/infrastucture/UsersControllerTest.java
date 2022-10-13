package com.thermostate.users.infrastucture;

import com.thermostate.users.application.CreateUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UsersControllerTest {
    CreateUser createUser;
    UsersController sut;

    @BeforeEach
    public void setup() {
        createUser = mock(CreateUser.class);
        sut = new UsersController(createUser);
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
}