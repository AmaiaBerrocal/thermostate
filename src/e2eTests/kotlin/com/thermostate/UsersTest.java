package com.thermostate;

import db.E2EDB;
import http.E2ERequest;
import http.E2EResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.thermostate.shared.HttpRequestsUtils.createSingleUser;
import static com.thermostate.shared.HttpRequestsUtils.getBearer;
import static org.assertj.core.api.Assertions.assertThat;

class UsersTest {
    E2EDB e2edb;
    @BeforeEach
    public void setup() {
        e2edb = new E2EDB("jdbc:sqlite:./assets/thermostate.db");
        e2edb.givenEmptyTable("USERS");
        createSingleUser(e2edb);
    }
    @Test
    void should_create_a_user() {
        createUser("Inigo", "pass", "lalo@gmail.com");

        e2edb
                .doQuery("SELECT * FROM USERS WHERE NAME = 'Inigo'")
                .assertThatExistAnEntryWithFields(Map.of(
                        "email", "lalo@gmail.com",
                        "active", 0));
    }

    @Test
    void should_fail_create_a_user_with_incorrect_dates() {
        E2ERequest
                .to("http://localhost:8080/user")
                .withHeader("Authorization", getBearer())
                .withContentType("application/json;charset=UTF-8")
                .sendAPost(Map.of("name", "", "password", "pass", "email", "lalo@gmail.com"))
                .assertThatResponseCodeIs(401);

        e2edb
                .doQuery("SELECT * FROM USERS WHERE email = 'lalo@gmail.com'")
                .assertThatNumberOfResults(0);
    }

    @Test
    void should_return_a_user_if_name_and_password_are_corrects() {
        //createUser("Inigo", "pass", "lalo@gmail.com");
        E2EResponse res = E2ERequest
                .to("http://localhost:8080/login")
                .withContentType("application/json")
                .sendAPost(Map.of("name", "amaia", "password", "pass"))
                .assertThatResponseIsOk();

        var responseBody = res.body();
        assertThat(responseBody.get("value").toString()).startsWith("Bearer ");
    }

    @Test
    void should_not_login_if_user_deactivated() {
        createUser("Inigo", "pass", "lalo@gmail.com");
        E2ERequest
                .to("http://localhost:8080/login")
                .withContentType("application/json")
                .sendAPost(Map.of("name", "INIGO", "password", "pass"))
                .assertThatResponseCodeIs(401);
    }


    @Test
    void should_return_an_error_if_password_is_incorrect() {
        createUser("Inigo", "incorrect pass", "lalo@gmail.com");
        E2ERequest
                .to("http://localhost:8080/login")
                .withContentType("application/json")
                .sendAPost(Map.of("name", "INIGO", "password", "pass"))
                .assertThatResponseCodeIs(401);
    }

    @Test
    void should_return_an_error_if_name_are_incorrects() {
        createUser("Inigo", "pass", "lalo@gmail.com");
        E2ERequest
                .to("http://localhost:8080/login")
                .withContentType("application/json")
                .sendAPost(Map.of("name", "INIGO-no", "password", "pass"))
                .assertThatResponseCodeIs(401);
    }

    void createUser(String name, String password, String email) {
        E2ERequest
                .to("http://127.0.0.1:8080/user")
                //.withABearer(HttpRequestsUtils::getBearer)
                .withHeader("Authorization", getBearer("Amaia", "pass"))
                .withContentType("application/json;charset=UTF-8")
                .sendAPost(Map.of("name", name, "password", password, "email", email))
                .assertThatResponseIsOk();
    }
}
