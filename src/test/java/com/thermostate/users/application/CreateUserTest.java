package com.thermostate.users.application;

import com.thermostate.schedules.model.events.EventBus;
import com.thermostate.shared.ClientError;
import com.thermostate.users.model.service.RandomStringGenerator;
import com.thermostate.users.model.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CreateUserTest {
    UserRepo userRepo;
    RandomStringGenerator randomStringGenerator;
    CreateUser sut;
    EventBus eventBus;

    @BeforeEach
    public void setup() {
        eventBus = mock(EventBus.class);
        userRepo = mock(UserRepo.class);
        randomStringGenerator = mock(RandomStringGenerator.class);
        sut = new CreateUser(userRepo, eventBus, randomStringGenerator);
    }

    @Test
    public void should_create_user_if_data_is_correct() {
        //given
        String name = "Felisa";
        String password = "ñlkjdalkejd";
        String email = "hahah@gmail.com";
        String salt = "salt";
        String hash = "lkjdalsjdwa";
        when(randomStringGenerator.generate()).thenReturn(salt);
        //when
        sut.execute(UUID.randomUUID(), name, password,email);
        //then
        //verify(userRepo).create(new User(null, name, hash, email, salt));
    }

    @Test
    public void should_not_create_user_if_name_is_incorrect() {
        //given
        String name = "";
        UUID uuid = UUID.randomUUID();
        String password = "ñlkjdalkejd";
        String email = "hahah@gmail.com";
        String salt = "salt";
        String hash = "lkjdalsjdwa";
        when(randomStringGenerator.generate()).thenReturn(salt);
        //when
        assertThatThrownBy( () -> {
            sut.execute(uuid, name, password,email);
        }
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(userRepo);
    }

    @Test
    public void should_not_create_user_if_password_is_incorrect() {
        //given
        String name = "Luisa";
        String password = null;
        String email = "hahah@gmail.com";
        when(randomStringGenerator.generate()).thenReturn(null);
        //when
        assertThatThrownBy( () -> {
                    sut.execute(UUID.randomUUID(), name, password,email);
                }
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(userRepo);
    }

    @Test
    public void should_not_create_user_if_email_is_incorrect() {
        //given
        String name = "Luisa";
        String password = "kjdlsjkaslf";
        String email = "hahahgmail.com";
        String salt = "salt";
        String hash = "lkjdalsjdwa";
        when(randomStringGenerator.generate()).thenReturn(salt);
        //when
        assertThatThrownBy( () -> {
                    sut.execute(UUID.randomUUID(), name, password,email);
                }
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(userRepo);
    }
}
