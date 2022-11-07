package com.thermostate.users.application;

import com.thermostate.shared.ClientError;
import com.thermostate.shared.HashGenerator;
import com.thermostate.shared.RandomStringGenerator;
import com.thermostate.users.model.User;
import com.thermostate.users.model.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CreateUserTest {
    UserRepo userRepo;
    RandomStringGenerator randomStringGenerator;
    HashGenerator hashGenerator;
    CreateUser sut;

    @BeforeEach
    public void setup() {
        userRepo = mock(UserRepo.class);
        randomStringGenerator = mock(RandomStringGenerator.class);
        hashGenerator = mock(HashGenerator.class);
        sut = new CreateUser(userRepo, randomStringGenerator, hashGenerator);
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
        when(hashGenerator.generate(password, salt)).thenReturn(hash);
        //when
        sut.execute(name, password,email);
        //then
        verify(userRepo).create(new User(null, name, hash, email, salt));
    }

    @Test
    public void should_not_create_user_if_name_is_incorrect() {
        //given
        String name = "";
        String password = "ñlkjdalkejd";
        String email = "hahah@gmail.com";
        String salt = "salt";
        String hash = "lkjdalsjdwa";
        when(randomStringGenerator.generate()).thenReturn(salt);
        when(hashGenerator.generate(password, salt)).thenReturn(hash);
        //when
        assertThatThrownBy( () -> {
            sut.execute(name, password,email);
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
        String salt = "salt";
        String hash = "lkjdalsjdwa";
        when(randomStringGenerator.generate()).thenReturn(salt);
        when(hashGenerator.generate(password, salt)).thenReturn(hash);
        //when
        assertThatThrownBy( () -> {
                    sut.execute(name, password,email);
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
        when(hashGenerator.generate(password, salt)).thenReturn(hash);
        //when
        assertThatThrownBy( () -> {
                    sut.execute(name, password,email);
                }
        ).isInstanceOf(ClientError.class);
        //then
        verifyNoInteractions(userRepo);
    }
}