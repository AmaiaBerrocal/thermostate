package com.thermostate.shared;

import db.E2EDB;
import http.E2ERequest;

import java.util.Map;
import java.util.UUID;

public class HttpRequestsUtils {
    public static String getBearer(String user, String password) {
        Map res = E2ERequest.to("http://127.0.0.1:8080/login")
                .withContentType("application/json;charset=UTF-8")
                .sendAPost(Map.of("password", password,
                        "name", user
                ))
                .body();
        System.out.println("Auth: " + res.get("value").toString());
        return res.get("value").toString();
    }
    public static String getBearer() {
        return getBearer("Amaia", "pass");
    }


    public static void createSingleUser(E2EDB db) {
        // name: "Amaia", password: "password", salt & email: "lala@gmail.com"
        db.given("""
                insert into users (name, password, salt, email, active, uuid) values (
                'Amaia',
                '71303db357e879fef8c74c25d65b81485c422b1ae1f344764775bbec3cf57aa3',
                '6G8x645HFazM', 
                'lala@gmail.com',
                1,
                 '""" + UUID.randomUUID() + "');");
    }
}
