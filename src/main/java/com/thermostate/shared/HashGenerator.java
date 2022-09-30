package com.thermostate.shared;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
@Component
public class HashGenerator {
    public String generate (String password, String salt) {
        String temp = password + salt;
        String hash = Hashing.sha256()
                .hashString(temp, StandardCharsets.UTF_8)
                .toString();
        return hash;
    }
}
