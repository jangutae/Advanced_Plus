package com.example.demo.user.entity;

public enum UserStatus {
    NORMAL("일반"),
    BLOCKED("차단");

    private final String name;

    UserStatus(String name) {
        this.name = name;
    }
}
