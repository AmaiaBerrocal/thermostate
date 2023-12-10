package com.thermostate.users.application;

import com.thermostate.spring.security.infrastucture.BearerService;
import com.thermostate.spring.security.model.TokenService;
import com.thermostate.shared.ClientError;
import com.thermostate.users.model.User;
import com.thermostate.users.model.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.thermostate.users.model.UserObjectMother.randomUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginUserTest {
    LoginUser sut;
    UserRepo userRepo;
    TokenService tokenService;
    EventBus eventBus;

    @BeforeEach
    void setup() {
        userRepo = mock(UserRepo.class);
        eventBus = mock(EventBus.class);
        tokenService = mock(BearerService.class);
        sut = new LoginUser(userRepo, eventBus, tokenService);
    }

    @Test
    public void should_return_null_if_user_does_not_exist() {
        //when
        assertThatThrownBy(() -> sut.execute("any", "a")).isInstanceOf(ClientError.class);
    }

    @Test
    public void should_return_null_if_hashes_are_different() {
        //given
        User user = randomUser("pass");
        when(userRepo.getByName(any())).thenReturn(user);
        //when
        assertThatThrownBy(() -> sut.execute(user.getName(), "random")).isInstanceOf(ClientError.class);

    }

    @Test
    public void should_return_user_if_exists_with_that_password() {
        //given
        User user = randomUser("pass");
        when(userRepo.getByName(any())).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn("token");
        //when
        var result = sut.execute(user.getName(), "pass");
        //then
        assertThat(result).isEqualTo("token");
    }
}
