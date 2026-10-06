package com.system.bank.enums;

public enum Role {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}