package com.thermostate.users.domain.service;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class HashGenerator {

    public static String generateRandomString() {
        int randomStringLength = 12;
        String cache = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String randomString = "";
        for (int i = 0; i < randomStringLength; i++) {
            Random r = new Random();
            int index = r.nextInt(cache.length());
            char randomChar = cache.charAt(index);
            randomString += randomChar;
        }
        return randomString;
    }

    public static String generateHashedPassword(String password, String salt) {
        String temp = password + salt;
        return Hashing.sha256()
                .hashString(temp, StandardCharsets.UTF_8)
                .toString();
    }
}
