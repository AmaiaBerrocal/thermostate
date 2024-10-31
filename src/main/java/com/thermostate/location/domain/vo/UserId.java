package com.thermostate.location.domain.vo;

import com.thermostate.shared.domain.ValueObject;

import java.util.UUID;

public class UserId extends ValueObject<UUID> {
    private UserId(UUID value) {
        super(value);
    }

    public static UserId from(UUID uuid) {
        return new UserId(uuid);
    }
}
