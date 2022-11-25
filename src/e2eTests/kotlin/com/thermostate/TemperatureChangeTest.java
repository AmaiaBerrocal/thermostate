package com.thermostate;

import db.E2EDB;
import http.E2ERequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.thermostate.shared.HttpRequestsUtils.createSingleUser;
import static com.thermostate.shared.HttpRequestsUtils.getBearer;

public class TemperatureChangeTest {
  E2EDB e2edb;
  @BeforeEach
  public void setup() {
    e2edb = new E2EDB("jdbc:sqlite:./db/thermostate.db");
    e2edb.givenEmptyTable("USERS");
    createSingleUser(e2edb);
  }

  @Test
  public void should_increase_temperature() {
    E2ERequest
        .to("ttp://localhost:8080/temperature/increment")
        .withHeader("Authorization", getBearer())
        .withContentType("application/json;charset=UTF-8")
        .sendAPut(Map.of("temperature", 100))
        .assertThatResponseIsOk();

    e2edb
        .doQuery("SELECT * FROM TEMPERATURE'")
        .assertThatNumberOfResults(1); //TODO check increasing with a 'get'?
  }

  @Test
  public void should_decrease_temperature() {
    E2ERequest
        .to("ttp://localhost:8080/temperature/decrease")
        .withHeader("Authorization", getBearer())
        .withContentType("application/json;charset=UTF-8")
        .sendAPut(Map.of("temperature", 100))
        .assertThatResponseIsOk();

    e2edb
        .doQuery("SELECT * FROM TEMPERATURE'")
        .assertThatNumberOfResults(1); //TODO check decreasing
  }
}
