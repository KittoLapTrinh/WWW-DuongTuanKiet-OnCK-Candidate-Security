package com.example.wwwduongtuankietoncksecurity.backend.enums;

public enum Roles {
    ADMINSTRATION(1),
    STAFF(2),
    MANAGER(3),
    EXECUTIVE(4);

    private final int value;

    Roles(int value) {
        this.value = value;
    }

    private int getValue() {
        return value;
    }


}
