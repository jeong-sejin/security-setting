package com.todo.securitySetting.entity;

import lombok.Getter;

@Getter
public enum Roles {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    GUEST("ROLE_GUEST");
    private String value;

    Roles(String value) {
        this.setValue(value);
    }

    private void setValue(String value) {
        this.value = value;
    }
}
