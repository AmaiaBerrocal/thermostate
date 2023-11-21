package com.thermostate.users.model.service;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
public class HashGenerator {
    public static String generate (String password, String salt) {
        String temp = password + salt;
        return Hashing.sha256()
                .hashString(temp, StandardCharsets.UTF_8)
                .toString();
    }
}
