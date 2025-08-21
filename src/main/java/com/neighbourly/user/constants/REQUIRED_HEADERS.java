package com.neighbourly.user.constants;

import lombok.Getter;

@Getter
public enum REQUIRED_HEADERS {
    UUID("UUID"),
    USER_ID("USER_ID");

    private final String value;

    REQUIRED_HEADERS(String value) {
        this.value = value;
    }
}
