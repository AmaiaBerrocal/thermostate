import http.Petition;
import org.junit.jupiter.api.Test;

import java.util.Map;

class UsersTest {

    @Test
    void should_create_a_user() {
        Petition
                .to("http://localhost:8080/user")
                .withContentType("application/json;charset=UTF-8")
                .sendAPost(Map.of("name", "Amaia", "password", "pass", "email", "lala@gmail.com"))
                .assertThatResponseIsOk();
    }
}