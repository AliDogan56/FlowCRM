package com.dogan.bilisim.domain.user;

import lombok.Getter;

@Getter
public enum UserRole {
    CUSTOMER("CUSTOMER"),
    SYSTEM_ADMIN("SYSTEM_ADMIN");

    private final String[] roles;

    UserRole(String... roles) {
        this.roles = roles;
    }

}
