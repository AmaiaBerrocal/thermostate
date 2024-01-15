package com.thermostate;

import com.thermostate.shared.HttpRequestsUtils;
import db.E2EDB;
import http.E2ERequest;
import http.E2EResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.thermostate.shared.HttpRequestsUtils.createSingleUser;

public class SchedulesTest {
    E2EDB e2edb;
    @BeforeEach
    public void setup() {
        e2edb = new E2EDB("jdbc:sqlite:./assets/thermostate.db");
        e2edb.givenEmptyTable("SCHEDULES");
        e2edb.givenEmptyTable("USERS");
        createSingleUser(e2edb);
    }

    @Test
    void should_create_an_schedule() {
        createScheduleWithPetition();
        E2EResponse res = E2ERequest
                .to("http://localhost:8080/schedules")
                .withABearer(HttpRequestsUtils::getBearer)
                .sendAGet(Map.of())
                .assertThatResponseIsOk();
        //Then
        res.assertThatBodyContains("""
                "weekDays":"L,M,X","timeFrom":"16:00","timeTo":"20:16","active":true,"minTemp":15
                """.trim());
    }

    @Test
    void should_fail_create_an_schedule_with_incorrect_dates() {
        E2ERequest
                .to("http://localhost:8080/schedule")
                .withABearer(HttpRequestsUtils::getBearer)
                .withContentType("application/json;charset=UTF-8")
                .sendAPost(Map.of("weekDays", "L,M,F",
                        "timeFrom", "16:00",
                        "timeTo", "20:16",
                        "active", "true",
                        "minTemp", "15"))
                .assertThatResponseCodeIs(400);
        e2edb
                .doQuery("SELECT * FROM SCHEDULES WHERE TIME_TO = '20:16'")
                .assertThatNumberOfResults(0);
    }

    @Test
    void should_return_all_schedules() {
        //given
        createScheduleWithPetition();
        //when
        E2EResponse res = E2ERequest
                .to("http://localhost:8080/schedules")
                .withABearer(HttpRequestsUtils::getBearer)
                .sendAGet(Map.of())
                .assertThatResponseIsOk();
        //Then
        res.assertThatBodyContains("""
                "weekDays":"L,M,X","timeFrom":"16:00","timeTo":"20:16","active":true,"minTemp":15
                """.trim());
    }
    //TODO
   @Test
    void should_delete_a_schedule() {
        //given
        createScheduleWithPetition();
        //when

        //then
    }
    void createScheduleWithPetition() {
        var response = E2ERequest
                .to("http://127.0.0.1:8080/schedule")
                .withABearer(HttpRequestsUtils::getBearer)
                .withContentType("application/json;charset=UTF-8")
                .sendAPost(Map.of("weekDays", "L,M,X",
                        "timeFrom", "16:00",
                        "timeTo", "20:16",
                        "active", "true",
                        "minTemp", "15"));
        response.assertThatResponseIsOk();
    }

    //TODO
    //void updateScheduleWithPetition() {}
}
