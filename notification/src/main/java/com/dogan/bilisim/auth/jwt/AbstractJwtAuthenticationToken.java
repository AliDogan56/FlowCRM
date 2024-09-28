package com.dogan.bilisim.auth.jwt;

import com.dogan.bilisim.domain.auth.securitUser.SecurityUser;
import com.dogan.bilisim.domain.auth.token.JwtToken;
import com.dogan.bilisim.domain.auth.token.RawAccessJwtToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.io.Serial;

public abstract class AbstractJwtAuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = -6212297506742428406L;

    private JwtToken rawAccessToken;
    private SecurityUser securityUser;

    protected AbstractJwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(null);
        this.rawAccessToken = unsafeToken;
        this.setAuthenticated(false);
    }

    protected AbstractJwtAuthenticationToken(SecurityUser securityUser) {
        super(securityUser.getAuthorities());
        this.eraseCredentials();
        this.securityUser = securityUser;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return this.securityUser;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.rawAccessToken = null;
    }
}
