import db.E2EDB;
import http.E2ERequest;
import org.junit.jupiter.api.Test;

import java.util.Map;

class UsersTest {

    @Test
    void should_create_a_user() {
        E2EDB e2edb = new E2EDB("jdbc:sqlite:./db/thermostate.db");
        e2edb.givenEmptyTable("USERS");

        E2ERequest
                .to("http://localhost:8080/user")
                .withContentType("application/json;charset=UTF-8")
                .sendAPost(Map.of("name", "Amaia", "password", "pass", "email", "lala@gmail.com"))
                .assertThatResponseIsOk();
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

    }
}