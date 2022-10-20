import db.E2EDB;
import http.E2ERequest;
import http.E2EResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class SchedulesTest {
    E2EDB e2edb;
    @BeforeEach
    public void setup() {
        e2edb = new E2EDB("jdbc:sqlite:./db/thermostate.db");
        e2edb.givenEmptyTable("SCHEDULES");
    }
    @Test
    void should_create_an_schedule() {
        E2ERequest
                .to("http://localhost:8080/schedule")
                .withContentType("application/json;charset=UTF-8")
                .sendAPost(Map.of("dateFrom", "2022-01-02",
                        "dateTo", "2023-03-04",
                        "timeFrom", "16:00",
                        "timeTo", "20:16",
                        "active", "true",
                        "minTemp", "15"))
                .assertThatResponseIsOk();
        e2edb
                .doQuery("SELECT * FROM SCHEDULES WHERE DATE_FROM = '2022-01-02'")
                .assertThatExistAnEntryWithFields(Map.of("date_to", "2023-03-04"));
    }

    @Test
    void should_fail_create_an_schedule_with_incorrect_dates() {
        E2ERequest
                .to("http://localhost:8080/schedule")
                .withContentType("application/json;charset=UTF-8")
                .sendAPost(Map.of("dateFrom", "2022-01-02",
                        "dateTo", "",
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
        E2ERequest
                .to("http://localhost:8080/schedule")
                .withContentType("application/json;charset=UTF-8")
                .sendAPost(Map.of("dateFrom", "2022-01-02",
                        "dateTo", "2023-03-04",
                        "timeFrom", "16:00",
                        "timeTo", "20:16",
                        "active", "true",
                        "minTemp", "15"));
        //when
        E2EResponse res = E2ERequest
                .to("http://localhost:8080/schedules")
                .sendAGet(Map.of())
                .assertThatResponseIsOk();
        //Then
        res.assertThatBodyContains(Map.of("dateFrom", "2022-01-02",
                "dateTo", "2023-03-04",
                "timeFrom", "16:00",
                "timeTo", "20:16",
                "active", "true",
                "minTemp", "15"));
    }

    /*
    @Test
    void should_return_schedule_with_id() {
        //given
        E2ERequest
                .to("http://localhost:8080/schedule")
                .withContentType("application/json;charset=UTF-8")
                .sendAPost(Map.of("dateFrom", "2022-01-02",
                        "dateTo", "2023-03-04",
                        "timeFrom", "16:00",
                        "timeTo", "20:16",
                        "active", "true",
                        "minTemp", "15"));
        //when
        E2EResponse res = E2ERequest
                .to("http://localhost:8080/schedule/")
                .sendAGet(Map.of())
                .assertThatResponseIsOk();
        //Then
        res.assertThatBodyContains(Map.of("dateFrom", "2022-01-02",
                "dateTo", "2023-03-04",
                "timeFrom", "16:00",
                "timeTo", "20:16",
                "active", "true",
                "minTemp", "15"));
    }
    */
}
