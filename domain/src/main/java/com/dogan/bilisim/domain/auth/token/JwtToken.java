package com.dogan.bilisim.domain.auth.token;

import java.io.Serializable;
import java.time.Instant;

public interface JwtToken extends Serializable {
    String getToken();

    String getTokenId();

    default AccessTokenType getAccessTokenType() {
        return AccessTokenType.BEARER;
    }

    default Instant getExpireDate() {
        return null;
    }
}
