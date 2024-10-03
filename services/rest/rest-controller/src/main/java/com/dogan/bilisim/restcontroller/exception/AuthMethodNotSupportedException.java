package com.dogan.bilisim.restcontroller.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

import java.io.Serial;

public class AuthMethodNotSupportedException extends AuthenticationServiceException {

    @Serial
    private static final long serialVersionUID = 3316160019733083570L;

    public AuthMethodNotSupportedException(String msg) {
        super(msg);
    }
}
