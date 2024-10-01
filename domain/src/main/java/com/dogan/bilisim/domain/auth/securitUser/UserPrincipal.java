package com.dogan.bilisim.domain.auth.securitUser;

import lombok.Getter;

import javax.security.auth.Subject;
import java.io.Serial;
import java.io.Serializable;
import java.security.Principal;

@Getter
public class UserPrincipal implements Serializable, Principal {

    @Serial
    private static final long serialVersionUID = 837143089354038653L;
    private final Type type;
    private final String value;

    public UserPrincipal(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.value;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }

    public enum Type {
        USER_NAME
    }

}
