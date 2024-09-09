package com.dogan.bilisim.domain.auth.token;

import com.dogan.bilisim.domain.auth.token.JwtToken;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Slf4j
public class RawAccessJwtToken implements JwtToken, Serializable {

    @Serial
    private static final long serialVersionUID = -797397445703066079L;

    private final String token;
    private final String jti;
    private final Instant expireDate;

    public RawAccessJwtToken(String token, final String jti, final Instant expireDate) {
        this.token = token;
        this.jti = jti;
        this.expireDate = expireDate;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getTokenId() {
        return this.jti;
    }

    @Override
    public Instant getExpireDate() {
        return this.expireDate;
    }
}
