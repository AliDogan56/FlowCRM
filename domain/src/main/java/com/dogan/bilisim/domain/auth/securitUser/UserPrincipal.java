package com.dogan.bilisim.domain.auth.securitUser;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class UserPrincipal implements Serializable {

    @Serial
    private static final long serialVersionUID = 837143089354038653L;
    private final Type type;
    private final String value;

    public UserPrincipal(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public enum Type {
        USER_NAME
    }

}
