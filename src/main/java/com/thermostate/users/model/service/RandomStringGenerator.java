package com.thermostate.users.model.service;

import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class RandomStringGenerator {
    public String generate() {
        int randomStringLength = 12;
        String cache = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String randomString = "";
        for (int i = 0; i < randomStringLength; i++) {
            Random r = new Random();
            int index = (int)r.nextInt(cache.length());
            char randomChar = cache.charAt(index);
            randomString += randomChar;
        }
        return randomString;
    }
}
