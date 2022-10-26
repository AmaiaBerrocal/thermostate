import db.E2EDB;
import http.E2ERequest;
import http.E2EResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class UsersTest {
    E2EDB e2edb;
    @BeforeEach
    public void setup() {
        e2edb = new E2EDB("jdbc:sqlite:./db/thermostate.db");
        //e2edb.givenEmptyTable("USERS");
    }
    @Test
    void should_create_a_user() {
        createUser("Amaia", "pass", "lala@gmail.com");

        e2edb
                .doQuery("SELECT * FROM USERS WHERE NAME = 'Amaia'")
                .assertThatExistAnEntryWithFields(Map.of("email", "lala@gmail.com"));
    }

    @Test
    void should_fail_create_a_user_with_incorrect_dates() {
        E2ERequest
                .to("http://localhost:8080/user")
                .withContentType("application/json;charset=UTF-8")
                .sendAPost(Map.of("name", "", "password", "pass", "email", "lala@gmail.com"))
                .assertThatResponseCodeIs(400);

        e2edb
                .doQuery("SELECT * FROM USERS WHERE email = 'lala@gmail.com'")
                .assertThatNumberOfResults(0);
    }

    @Test
    void should_return_a_user_if_name_and_password_are_corrects() {
        //createUser("Amaia", "pass", "lala@gmail.com");
        E2EResponse res = E2ERequest
                .to("http://localhost:8080/login/")
                .sendAPost(Map.of("name", "Amaia", "password", "pass"))
                .assertThatResponseIsOk();

        res.getReturned().body();
        res.assertThatBodyContains(Map.of("name", "Amaia", "email", "lala@gmail.com"));
    }

    void createUser(String name, String password, String email) {
        E2ERequest
                .to("http://localhost:8080/user")
                .withContentType("application/json;charset=UTF-8")
                .sendAPost(Map.of("name", name, "password", password, "email", email))
                .assertThatResponseIsOk();
    }
}
