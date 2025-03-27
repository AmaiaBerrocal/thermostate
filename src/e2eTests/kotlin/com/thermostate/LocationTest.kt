package com.thermostate

import com.google.gson.Gson
import com.thermostate.shared.HttpRequestsUtils
import db.E2EDB
import http.E2ERequest
import http.E2EResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import java.util.Map

class LocationTest {
    var e2edb: E2EDB? = null
/*
    @BeforeEach
    fun setup() {
        e2edb = E2EDB("jdbc:sqlite:./assets/thermostate.db")
        e2edb!!.givenEmptyTable("LOCATIONS")
        e2edb!!.givenEmptyTable("USERS")
        HttpRequestsUtils.createSingleUser(e2edb)
    }

    @Test
    fun `should save a location`() {
        var uuid = UUID.randomUUID()
        E2ERequest.to("http://localhost:8080/geolocation")
            .withHeader("Authorization", HttpRequestsUtils.getBearer())
            .withContentType("application/json;charset=UTF-8")
            .sendAPut(Map.of("userId", uuid,
                "latitude", 42,
                "longitude", 43))
            .assertThatResponseIsOk()

        e2edb!!.doQuery("Select * from locations")
            .assertThatNumberOfResults(1)
    }

    @Test
    fun `should find a location`() {
        e2edb!!.given("""
            Insert into locations (latitude, longitude, created_at, user_id) values (42, 43, datetime(), '${UUID.randomUUID()}');
            Insert into locations (latitude, longitude, created_at, user_id) values (44, 45, datetime(), '${UUID.randomUUID()}');
            Insert into locations (latitude, longitude, created_at, user_id) values (42, 46, datetime(), '${UUID.randomUUID()}');
        """.trimIndent())

        val req = Gson().toJson(Map.of("filters", listOf(Map.of("field", "latitude",
                                    "option", "EQ",
                                    "value", 42)),
                                "orderBy", Map.of("field", "longitude", "order", "DESC"),
                                "limit", 1)
        )

        val bearer = HttpRequestsUtils.getBearer()
        println(bearer)
        println(req)
        E2ERequest.to("http://127.0.0.1:8080/geolocation")
            .withHeader("Authorization", bearer)
            .withContentType("application/json;charset=UTF-8")
            .sendAPost(req)
            .assertThatResponseIsOk()
    }

    @Test
    fun `should find a location of a specific user`() {
        val userId = UUID.randomUUID();
        e2edb!!.given("""
            Insert into locations (latitude, longitude, created_at, user_id) values (42, 43, datetime(), '$userId');
            Insert into locations (latitude, longitude, created_at, user_id) values (44, 45, datetime()+1, '$userId');
            Insert into locations (latitude, longitude, created_at, user_id) values (42, 46, datetime()+2, '$userId');
        """.trimIndent())

        val req = Gson().toJson(Map.of("filters", listOf(Map.of("field", "user_id",
            "option", "EQ",
            "value", userId)),
            "orderBy", Map.of("field", "longitude", "order", "DESC"),
            "limit", 1)
        )

        val bearer = HttpRequestsUtils.getBearer()
        println(bearer)
        println(req)
        val res = E2ERequest.to("http://127.0.0.1:8080/geolocation")
            .withHeader("Authorization", bearer)
            .withContentType("application/json;charset=UTF-8")
            .sendAPost(req)
            .assertThatResponseIsOk()
        println(res)
    }*/
}