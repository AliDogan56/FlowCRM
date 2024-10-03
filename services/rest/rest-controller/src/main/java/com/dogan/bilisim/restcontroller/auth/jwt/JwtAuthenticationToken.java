package com.dogan.bilisim.restcontroller.auth.jwt;


import com.dogan.bilisim.domain.auth.securitUser.SecurityUser;
import com.dogan.bilisim.domain.auth.token.RawAccessJwtToken;

public class JwtAuthenticationToken extends AbstractJwtAuthenticationToken {


    public JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(unsafeToken);
    }

    public JwtAuthenticationToken(SecurityUser securityUser) {
        super(securityUser);
    }
}
