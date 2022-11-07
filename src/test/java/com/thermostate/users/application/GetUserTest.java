package com.thermostate.users.application;

import com.google.common.eventbus.EventBus;
import com.thermostate.shared.HashGenerator;
import com.thermostate.users.model.User;
import com.thermostate.users.model.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.thermostate.users.model.UserObjectMother.randomUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetUserTest {
    GetUser sut;
    UserRepo userRepo;
    HashGenerator hashGenerator;
    EventBus eventBus;

    @BeforeEach
    void setup() {
        userRepo = mock(UserRepo.class);
        hashGenerator = mock(HashGenerator.class);
        eventBus = mock(EventBus.class);
        sut = new GetUser(userRepo, hashGenerator, eventBus);
    }

    @Test
    public void should_return_null_if_user_does_not_exist() {
        //given
        //when
        User result = sut.execute("d", "a");
        //then
        assertNull(result);
    }

    @Test
    public void should_return_null_if_hashes_are_different() {
        //given
        User user = randomUser();
        when(userRepo.getByName(any())).thenReturn(user);
        when(hashGenerator.generate(any(), any())).thenReturn(user.password() + "3");
        //when
        User result = sut.execute(user.name(), "a");
        //then
        assertNull(result);
    }

    @Test
    public void should_return_user_if_exists_with_that_password() {
        //given
        User user = randomUser();
        when(userRepo.getByName(any())).thenReturn(user);
        when(hashGenerator.generate(any(), any())).thenReturn(user.password());
        //when
        User result = sut.execute(user.name(), "a");
        //then
        assertThat(result).isEqualTo(user);
    }
}