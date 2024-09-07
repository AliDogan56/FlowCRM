package com.dogan.bilisim.auth.jwt;


import com.dogan.bilisim.domain.auth.token.RawAccessJwtToken;
import com.dogan.bilisim.domain.auth.securitUser.SecurityUser;

public class JwtAuthenticationToken extends AbstractJwtAuthenticationToken {


    public JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(unsafeToken);
    }

    public JwtAuthenticationToken(SecurityUser securityUser) {
        super(securityUser);
    }
}
