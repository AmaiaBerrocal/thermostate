package com.thermostate.users.domain;

import com.thermostate.users.infrastucture.data.UserRole;
import lombok.Getter;

import java.util.UUID;

@Getter
public class LogedUserInfo {
    private String userId;
    private String bearer;
    private String role;

    public LogedUserInfo(UUID userId, String bearer, String role) {
        this.userId = userId.toString();
        this.bearer = bearer;
        this.role = role;
    }

    public static LogedUserInfo from(UUID userId, String bearer, UserRole role) {
        return new LogedUserInfo(userId, bearer, role.name());
    }
}
