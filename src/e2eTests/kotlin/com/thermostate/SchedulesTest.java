package com.thermostate;

import com.thermostate.shared.HttpRequestsUtils;
import db.E2EDB;
import http.E2ERequest;
import http.E2EResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

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
        createSchedule(UUID.randomUUID());
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
        createSchedule(UUID.randomUUID());
        //when
        E2EResponse res = E2ERequest
                .to("http://localhost:8080/schedules")
                .withABearer(HttpRequestsUtils::getBearer)
                .sendAGet(Map.of()) //TODO: overload this
                .assertThatResponseIsOk();
        //Then
        res.assertThatBodyContains("""
                "weekDays":"L,M,X","timeFrom":"16:00","timeTo":"20:16","active":true,"minTemp":15
                """.trim());
    }

   @Test
    void should_delete_a_schedule() {
        //given
        UUID uuid = UUID.randomUUID();
        createSchedule(uuid);
        //when
       E2ERequest
               .to("http://localhost:8080/schedule/" + uuid)
               .withABearer(HttpRequestsUtils::getBearer)
               .sendADelete("") //TODO: fix this
               .assertThatResponseIsOk();
        //then
       E2ERequest
               .to("http://localhost:8080/schedule/" + uuid)
               .withABearer(HttpRequestsUtils::getBearer)
               .sendAGet(Map.of())
               .assertThatResponseCodeIs(401); //TODO at this moment all client errors are transalted to 401
    }

    @Test
    void should_return_given_schedule() {
        //given
        UUID uuid = UUID.randomUUID();
        createSchedule(uuid);
        //when
        var res = E2ERequest
                .to("http://localhost:8080/schedule/" + uuid)
                .withABearer(HttpRequestsUtils::getBearer)
                .sendAGet(Map.of())
                .assertThatResponseIsOk();
        //then
        res.assertThatBodyContains("""
                "weekDays":"L,M,X","timeFrom":"16:00","timeTo":"20:16","active":true,"minTemp":15
                """.trim());
    }

    @Test
    void updateScheduleWithPetition() {
        //given
        UUID uuid = UUID.randomUUID();
        createSchedule(uuid);
        //when
        E2ERequest
                .to("http://127.0.0.1:8080/schedule")
                .withABearer(HttpRequestsUtils::getBearer)
                .withContentType("application/json;charset=UTF-8")
                .sendAPut(Map.of("weekDays", "L,M,X,J",
                        "timeFrom", "17:00",
                        "timeTo", "20:17",
                        "active", false,
                        "minTemp", "17",
                        "id", uuid.toString()));
        //then
        var res = E2ERequest
                .to("http://localhost:8080/schedule/" + uuid)
                .withABearer(HttpRequestsUtils::getBearer)
                .sendAGet(Map.of())
                .assertThatResponseIsOk();

        res.assertThatBodyContains("""
                "weekDays":"L,M,X,J"
                """.trim());
    }
    void createSchedule(UUID uuid) {
        var response = E2ERequest
                .to("http://127.0.0.1:8080/schedule")
                .withABearer(HttpRequestsUtils::getBearer)
                .withContentType("application/json;charset=UTF-8")
                .sendAPost(Map.of("weekDays", "L,M,X",
                        "timeFrom", "16:00",
                        "timeTo", "20:16",
                        "active", "true",
                        "minTemp", "15",
                        "id", uuid.toString()));
        response.assertThatResponseIsOk();
    }

}
