package com.dogan.bilisim.notificationcontroller.auth.jwt;

import com.dogan.bilisim.domain.auth.securitUser.SecurityUser;
import com.dogan.bilisim.domain.auth.securitUser.SecurityUserDetails;
import com.dogan.bilisim.domain.auth.securitUser.UserPrincipal;
import com.dogan.bilisim.domain.auth.token.RawAccessJwtToken;
import com.dogan.bilisim.notificationservice.auth.AuthenticationService;
import com.dogan.bilisim.notificationservice.auth.jwt.JwtTokenFactory;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private JwtTokenFactory tokenFactory;
    private AuthenticationService authenticationService;

    @Autowired
    public JwtAuthenticationProvider(JwtTokenFactory tokenFactory, AuthenticationService authenticationService) {
        this.tokenFactory = tokenFactory;
        this.authenticationService = authenticationService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        if (tokenFactory.isExpired(rawAccessToken.getToken())) {
            throw new BadCredentialsException("ApiMessages.JWTTokenExpired");
        }
        final Optional<SecurityUserDetails> byToken = authenticationService.findByToken(rawAccessToken.getToken());
        if (byToken.isEmpty()) {
            throw new BadCredentialsException("Jwt Token revoked");
        }

        final UserPrincipal userPrincipal = new UserPrincipal(UserPrincipal.Type.USER_NAME, byToken.get().getUsername());
        return new JwtAuthenticationToken(new SecurityUser(byToken.get().getAuthorities(), true, userPrincipal));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (JwtAuthenticationToken.class.isAssignableFrom(aClass));
    }
}