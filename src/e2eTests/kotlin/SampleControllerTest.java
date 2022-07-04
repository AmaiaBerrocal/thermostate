import http.Petition;
import org.junit.jupiter.api.Test;

import java.util.Map;

class SampleControllerTest {

    @Test
    void helloWorld() {
        Petition
                .to("http://localhost:8080/helloworld")
                .sendAGet(Map.of())
                .assertThatResponseIsOk()
                .assertThatBodyContains("Hello world!");
    }
}