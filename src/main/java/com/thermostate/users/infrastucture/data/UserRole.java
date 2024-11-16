package com.thermostate.users.infrastucture.data;

import java.util.Arrays;

public enum UserRole {
    THERMOSTAT_USER(2),
    LOCALIZABLE_USER(1),
    pa(0);

    public final Integer value;

    UserRole(int value) {
        this.value = value;
    }

    public static UserRole valueOf(Integer type) {
        return Arrays.stream(UserRole.values()).filter(userRole -> userRole.value.equals(type)).findFirst()
                .orElseThrow(() -> new EnumConstantNotPresentException(UserRole.class, "" + type));
    }
}
