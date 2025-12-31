package com.example.ChatApp.Domain.ValueObject;

public enum Gender {
    UNKNOW(0),
    MALE(1),
    FEMALE(2);

    public final int value;
    Gender(int value) {
        this.value = value;
    }

    public static Gender fromCode(int value) {
        for (Gender s : values()) {
            if (s.value == value) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid Gender value: " + value);
    }

    public static Gender tryFrom(int value) {
        for (Gender s : values()) {
            if (s.value == value) {
                return s;
            }
        }

        return null;
    }
}
