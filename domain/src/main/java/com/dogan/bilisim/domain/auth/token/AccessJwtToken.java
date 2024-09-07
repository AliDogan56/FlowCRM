package com.dogan.bilisim.domain.auth.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;

import java.time.Instant;

public final class AccessJwtToken implements JwtToken {
    private final String rawToken;
    @JsonIgnore
    private transient Claims claims;
    @JsonIgnore
    private final String jti;
    private final Instant expireDate;

    public AccessJwtToken(final String token, final String jti, final Claims claims, final Instant expireDate) {
        this.rawToken = token;
        this.jti = jti;
        this.claims = claims;
        this.expireDate=expireDate;
    }

    @Override
    public Instant getExpireDate() {
        return this.expireDate;
    }

    public String getToken() {
        return this.rawToken;
    }

    @Override
    public String getTokenId() {
        return this.jti;
    }

    public Claims getClaims() {
        return claims;
    }
}
