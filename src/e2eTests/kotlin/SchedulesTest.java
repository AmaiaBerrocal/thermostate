import db.E2EDB;
import http.E2ERequest;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class SchedulesTest {

    @Test
    void should_create_an_schedule() {
        E2EDB e2edb = new E2EDB("jdbc:sqlite:./db/thermostate.db");
        e2edb.givenEmptyTable("SCHEDULES");

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
        E2EDB e2edb = new E2EDB("jdbc:sqlite:./db/thermostate.db");
        e2edb.givenEmptyTable("SCHEDULES");

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
}
