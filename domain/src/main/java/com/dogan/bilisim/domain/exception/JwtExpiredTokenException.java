package com.dogan.bilisim.domain.exception;

import com.dogan.bilisim.domain.auth.token.JwtToken;
import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

public class JwtExpiredTokenException extends AuthenticationException {
    @Serial
    private static final long serialVersionUID = -5959543783324224864L;

    private JwtToken token;

    public JwtExpiredTokenException(String msg) {
        super(msg);
    }

    public JwtExpiredTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
