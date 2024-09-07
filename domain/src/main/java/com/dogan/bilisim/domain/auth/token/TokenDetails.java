package com.dogan.bilisim.domain.auth.token;

import com.dogan.bilisim.domain.auth.token.AccessTokenType;
import com.dogan.bilisim.domain.auth.token.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class TokenDetails implements JwtToken {
    private final String token;
    private final AccessTokenType accessTokenType;
    private final String tokenId;
    private final String userId;
    private final Instant expireDate;
}
