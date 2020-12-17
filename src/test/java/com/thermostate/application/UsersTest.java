package com.thermostate.application;

import com.thermostate.dominio.User;
import com.thermostate.infrastructure.SqulitoConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UsersTest {
    SqulitoConnection conn;

    @BeforeEach
    public void setup() {
        conn = mock(SqulitoConnection.class);
    }

    @Test
    public void should_return_true_if_user_with_given_password_exists(){
        User user = new User("name","password", "salt");
        when(conn.findUserByUsername(any())).thenReturn(user);

        Users users = new Users(conn);
        assertTrue(users.isValidPassword("name", "password"));
    }

    @Test
    public void should_return_false_if_user_with_given_password_does_not_exists(){
        User user = new User("name","password", "salt");
        when(conn.findUserByUsername(any())).thenReturn(user);

        Users users = new Users(conn);
        assertFalse(users.isValidPassword("name", "tutifruti"));
    }

    @Test
    public void should_return_false_if_user_does_not_exist(){
        when(conn.findUserByUsername(any())).thenReturn(null);

        Users users = new Users(conn);
        assertFalse(users.isValidPassword("nombre", "password"));
    }
}