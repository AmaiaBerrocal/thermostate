package com.thermostate.shared;

import com.thermostate.users.domain.User;

public class ClientError extends RuntimeException {
    private ClientError(String message) {
        super(message);
    }

    public static ClientError becauseInvalidDataFromClient() {
        return new ClientError("Invalid data from client");
    }

    public static ClientError becauseDeactivatedUser(User loginUser) {
        return new ClientError("User " + loginUser.getName() + " is not active");
    }
}
