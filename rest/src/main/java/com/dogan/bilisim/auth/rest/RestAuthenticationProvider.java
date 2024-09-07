package com.dogan.bilisim.auth.rest;

import com.dogan.bilisim.domain.auth.securitUser.SecurityUser;
import com.dogan.bilisim.domain.auth.securitUser.UserPrincipal;
import com.dogan.bilisim.domain.user.AppUser;
import com.dogan.bilisim.exception.AuthMethodNotSupportedException;
import com.dogan.bilisim.service.user.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class RestAuthenticationProvider implements AuthenticationProvider {

    private final AppUserService appUserService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RestAuthenticationProvider(final AppUserService appUserService, PasswordEncoder passwordEncoder) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserPrincipal userPrincipal)) {
            throw new BadCredentialsException("Authentication Failed. Bad user principal.");
        }

        if (userPrincipal.getType() == UserPrincipal.Type.USER_NAME) {
            String username = userPrincipal.getValue();
            String password = (String) authentication.getCredentials();
            return authenticateByUsernameAndPassword(authentication, userPrincipal, username, password);
        } else {
            throw new AuthMethodNotSupportedException(userPrincipal.getType() + " Authentication Method is Not Supported yet");
        }
    }

    private Authentication authenticateByUsernameAndPassword(Authentication authentication, UserPrincipal userPrincipal, String username, String password) {
        Optional<AppUser> optionalAppUser = appUserService.findUserByUsername(username);

        if (optionalAppUser.isEmpty())
            throw new BadCredentialsException("ApiMessages.UserNotFoundWithUsername, " + username);

        final AppUser appUser = optionalAppUser.get();

        if ((!appUser.getUsername().equalsIgnoreCase(username)))
            throw new BadCredentialsException("ApiMessages.UserNotFoundWithUsername, " + username);

        if (!passwordEncoder.matches(password, appUser.getPassword())) {
            throw new BadCredentialsException("ApiMessages.InvalidUsernameOrPassword");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (String authority : appUser.getRole().getRoles()) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }

        SecurityUser securityUser = new SecurityUser(
                authorities,
                true,
                userPrincipal);
        return new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
