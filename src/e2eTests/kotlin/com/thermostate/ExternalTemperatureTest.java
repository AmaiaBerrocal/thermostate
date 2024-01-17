package com.thermostate;

import db.E2EDB;
import org.junit.jupiter.api.BeforeEach;

import static com.thermostate.shared.HttpRequestsUtils.createSingleUser;

public class ExternalTemperatureTest {
    E2EDB e2edb;
    @BeforeEach
    public void setup() {
        e2edb = new E2EDB("jdbc:sqlite:./assets/thermostate.db");
        e2edb.givenEmptyTable("SCHEDULES");
        e2edb.givenEmptyTable("USERS");
        createSingleUser(e2edb);
    }

}
