package com.dogan.bilisim.domain.user;

import lombok.Getter;

@Getter
public enum UserRole {

    SYSTEM_OWNER("SYSTEM_OWNER", "SYSTEM_ADMIN", "CUSTOMER"),
    SYSTEM_ADMIN("SYSTEM_ADMIN", "CUSTOMER"),
    CUSTOMER("CUSTOMER");


    private final String[] roles;

    UserRole(String... roles) {
        this.roles = roles;
    }

}
