package com.thermostate.shared.domain.criteria;

public enum Order {
    ASC(" ASC"),
    DESC(" DESC");

    public final String value;

    Order(String order) {
        this.value = order;
    }
}
