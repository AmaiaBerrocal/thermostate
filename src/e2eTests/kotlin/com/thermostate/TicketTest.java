package com.thermostate;

import db.E2EDB;
import http.E2ERequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.thermostate.shared.HttpRequestsUtils.createSingleUser;
import static com.thermostate.shared.HttpRequestsUtils.getBearer;

public class TicketTest {
    E2EDB e2edb;
    @BeforeEach
    public void setup() {
        e2edb = new E2EDB("jdbc:sqlite:./assets/thermostate.db");
        e2edb.givenEmptyTable("USERS");
        createSingleUser(e2edb);
    }

    @Test
    public void should_create_a_ticket() {
        E2ERequest
                .to("http://localhost:8080/upload")
                .withHeader("Authorization", getBearer())
                .withContentType("application/form-data;charset=UTF-8")
                .sendAFilePost("/home/inigo/projects/thermostate/src/test/resources/Compra.pdf", "file",
                        Map.of("fileName", "testticket.pdf"))
                .assertThatResponseIsOk();

    }

    @Test
    public void should_create_a_ticket_opnce_when_asked_twice() {
        String bearer = getBearer();
        E2ERequest
                .to("http://localhost:8080/upload")
                .withHeader("Authorization", bearer)
                .withContentType("application/form-data;charset=UTF-8")
                .sendAFilePost("/home/inigo/projects/thermostate/src/test/resources/Compra.pdf", "file",
                        Map.of("fileName", "testticket.pdf"))
                .assertThatResponseIsOk();
        E2ERequest
                .to("http://localhost:8080/upload")
                .withHeader("Authorization", bearer)
                .withContentType("application/form-data;charset=UTF-8")
                .sendAFilePost("/home/inigo/projects/thermostate/src/test/resources/Compra.pdf", "file",
                        Map.of("fileName", "testticket.pdf"))
                .assertThatResponseIsOk();

    }
}
