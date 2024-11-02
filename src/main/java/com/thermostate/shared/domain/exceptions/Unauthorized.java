package com.thermostate.shared.domain.exceptions;

import com.thermostate.users.infrastucture.data.UserType;

public class Unauthorized extends RuntimeException {
    public Unauthorized(String message) {
        super(message);
    }

    public static Unauthorized becauseNotAbleToManageSchedules(UserType userType) {
        return new Unauthorized("A user of type " + userType + " is not authorized to manipulate schedules");
    }

    public static Unauthorized becauseNotAbleToCreateUsers(UserType userType) {
        return new Unauthorized("A user of type " + userType + " is not authorized to create new users");
    }
}
