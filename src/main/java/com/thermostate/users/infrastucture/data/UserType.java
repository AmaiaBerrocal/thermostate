package com.thermostate.users.infrastucture.data;

import java.util.Arrays;

public enum UserType {
    THERMOSTAT_USER(2),
    LOCALIZABLE_USER(1),
    ADMIN(0);

    public final Integer value;

    UserType(int value) {
        this.value = value;
    }

    public static UserType valueOf(Integer type) {
        return Arrays.stream(UserType.values()).filter(userType -> userType.value.equals(type)).findFirst()
                .orElseThrow(() -> new EnumConstantNotPresentException(UserType.class, "" + type));
    }
}
