package com.thermostate.infrastructure.db;

import com.thermostate.users.infrastucture.data.UserJpa;
import com.thermostate.users.infrastucture.data.UserJpaRepo;
import com.thermostate.users.infrastucture.data.Users;
import com.thermostate.users.model.User;
import com.thermostate.users.model.UserObjectMother;
import com.thermostate.users.model.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserDbRepoTest {

    Users userRepo;

    @BeforeEach
    public void setup() {
        userRepo = mock(Users.class);
    }

    @Test
    public void user_should_be_insert() {
        //given
        UserRepo sut = new UserJpaRepo(userRepo);
        User user = UserObjectMother.randomUser("pass");
        //when
        sut.create(user);
        //then
        ArgumentCaptor<UserJpa> captor = ArgumentCaptor.forClass(UserJpa.class);
        verify(userRepo).save(captor.capture());
        assertThat(UserJpa.fromDomain(user))
                .usingRecursiveComparison()
                .isEqualTo(captor.getValue());
    }
}
