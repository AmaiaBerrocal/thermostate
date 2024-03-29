package com.thermostate;

import com.google.gson.Gson;
import com.thermostate.shared.domain.Temperature;
import db.E2EDB;
import http.E2ERequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.thermostate.shared.HttpRequestsUtils.createSingleUser;
import static com.thermostate.shared.HttpRequestsUtils.getBearer;
import static org.assertj.core.api.Assertions.assertThat;

public class TemperatureChangeTest {
  E2EDB e2edb;
  @BeforeEach
  public void setup() {
    e2edb = new E2EDB("jdbc:sqlite:./assets/thermostate.db");
    e2edb.givenEmptyTable("USERS");
    createSingleUser(e2edb);
  }

  @Test
  public void should_increase_temperature() {
    var previousTemp = getActualTemperature();
    E2ERequest
        .to("http://localhost:8080/temperature/increment")
        .withHeader("Authorization", getBearer())
        .withContentType("application/json;charset=UTF-8")
        .sendAPut(Map.of("temperature", 100))
        .assertThatResponseIsOk();

    assertThat(getActualTemperature()).isEqualTo(previousTemp + 100);
  }

  @Test
  public void should_decrease_temperature() {
    var previousTemp = getActualTemperature();
    E2ERequest
        .to("http://localhost:8080/temperature/decrement")
        .withHeader("Authorization", getBearer())
        .withContentType("application/json;charset=UTF-8")
        .sendAPut(Map.of("temperature", 100))
        .assertThatResponseIsOk();

    assertThat(getActualTemperature()).isEqualTo(previousTemp - 100);
  }

  private Integer getActualTemperature() {
    var response = E2ERequest
        .to("http://localhost:8080/temperature")
        .withHeader("Authorization", getBearer())
        .withContentType("application/json;charset=UTF-8")
        .sendAGet(Map.of());

    var gson = new Gson();

    return gson.fromJson(response.body().get("value").toString(), Temperature.class).getTemp();
  }
}
