package com.dogan.bilisim.auth.jwt;

import com.dogan.bilisim.domain.auth.token.JwtToken;
import com.dogan.bilisim.domain.auth.securitUser.SecurityUser;
import com.dogan.bilisim.service.auth.jwt.JwtTokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenRepository {

    private final JwtTokenFactory tokenFactory;

    @Autowired
    public RefreshTokenRepository(final JwtTokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    public JwtToken requestRefreshToken(SecurityUser user) {
        return tokenFactory.generateToken(user);
    }

}
