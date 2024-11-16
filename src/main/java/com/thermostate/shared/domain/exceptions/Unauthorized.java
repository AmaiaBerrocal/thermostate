package com.thermostate.shared.domain.exceptions;

import com.thermostate.users.infrastucture.data.UserRole;

public class Unauthorized extends RuntimeException {
    public Unauthorized(String message) {
        super(message);
    }

    public static Unauthorized becauseNotAbleToManageSchedules(UserRole userRole) {
        return new Unauthorized("A user of type " + userRole + " is not authorized to manipulate schedules");
    }

    public static Unauthorized becauseNotAbleToCreateUsers(UserRole userRole) {
        return new Unauthorized("A user of type " + userRole + " is not authorized to create new users");
    }
}
