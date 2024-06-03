package com.thermostate;

import com.thermostate.brain.domain.ThermostateStatus;
import db.E2EDB;
import http.E2ERequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.thermostate.shared.HttpRequestsUtils.createSingleUser;
import static com.thermostate.shared.HttpRequestsUtils.getBearer;
import static org.assertj.core.api.Assertions.assertThat;

public class StatusTest {
    E2EDB e2edb;
    @BeforeEach
    public void setup() {
        e2edb = new E2EDB("jdbc:sqlite:./assets/thermostate.db");
        e2edb.givenEmptyTable("SCHEDULES");
        e2edb.givenEmptyTable("USERS");
        createSingleUser(e2edb);
    }

    @Test
    public void should_return_status() {
        var response = E2ERequest
                .to("http://localhost:8080/status")
                .withHeader("Authorization", getBearer())
                .withContentType("application/json;charset=UTF-8")
                .sendAGet(Map.of())
                .assertThatResponseIsOk();

        var map = (Map<String, Object>)response.body().get("value"); // TODO: fix this
        assertThat(((Map<String, Object>)map.get("currentTargetTemperature")).get("temp")).isNotInstanceOf(Integer.class);
        assertThat(((Map<String, Object>)map.get("currentTemperature")).get("temp")).isNotInstanceOf(Integer.class);
        assertThat(((Map<String, Object>)map.get("externalTemperature")).get("temp")).isNotInstanceOf(Integer.class);
        assertThat((map.get("active"))).isEqualTo(true);
    }
}
