package com.thermostate.shared;

public class ClientError extends RuntimeException {
    private ClientError(String message) {
        super(message);
    }

    public static ClientError becauseInvalidDataFromClient() {
        return new ClientError("Invalid data from client");
    }
}
