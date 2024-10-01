package com.dogan.bilisim.service.auth;

import com.dogan.bilisim.dao.auth.AccessTokenRepository;
import com.dogan.bilisim.domain.auth.token.AccessToken;
import com.dogan.bilisim.domain.auth.securitUser.SecurityUserDetails;
import com.dogan.bilisim.domain.auth.token.TokenDetails;
import com.dogan.bilisim.domain.exception.JwtExpiredTokenException;
import com.dogan.bilisim.domain.user.AppUser;
import com.dogan.bilisim.service.auth.jwt.JwtTokenFactory;
import com.dogan.bilisim.service.auth.jwt.TokenExtractor;
import com.dogan.bilisim.service.auth.util.JwtUtil;
import com.dogan.bilisim.service.user.AppUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService implements UserAuthenticationService {

    @NotNull
    private PasswordEncoder passwordEncoder;
    @NotNull
    private JwtUtil jwtUtil;
    @NotNull
    private JwtTokenFactory jwtTokenFactory;
    @NotNull
    private AppUserService appUserService;
    @NotNull
    private AccessTokenRepository accessTokenRepository;

    @NotNull
    private TokenExtractor tokenExtractor;

    @Transactional
    @Override
    public Optional<SecurityUserDetails> login(String username, String password) {
        if (!StringUtils.hasLength(username) ||
                !StringUtils.hasLength(password))
            throw new IllegalArgumentException("ApiMessages.InvalidUsernameOrPassword");

        return appUserService.findUserByUsername(username)
                .filter(appUser -> passwordEncoder.matches(password, appUser.getPassword()))
                .map(appUser -> {
                    final List<String> roles = Arrays.asList(appUser.getRole().getRoles());
                    final TokenDetails tokenDetails = jwtTokenFactory.generateToken(appUser.getUsername(), roles, appUser.getUsername());
                    return Optional.of(new SecurityUserDetails(tokenDetails,
                            appUser.getId(),
                            appUser.getUsername(),
                            passwordEncoder.encode(appUser.getPassword()),
                            roles));
                }).get();
    }

    @Transactional
    @Override
    public Optional<SecurityUserDetails> findByToken(String token) {
        final Claims claims;
        try {
            claims = jwtUtil.getBody(token);
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredTokenException("ApiMessages.JWTTokenExpired");
        }
        final Optional<AccessToken> byJti = accessTokenRepository.findByJti(claims.getId());
        if (byJti.isEmpty()) {
            throw new AuthenticationServiceException("ApiMessages.TokenRevoked");
        }

        String username = claims.getSubject();
        AppUser user = appUserService.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("ApiMessages.InvalidUsernameWithUsername, " + username));
        final List<String> scopes = claims.get(JwtTokenFactory.SCOPES, List.class);

        List<String> roles = Arrays.asList(user.getRole().getRoles());

        roles.forEach(role -> {
            if (!scopes.contains(role)) {
                throw new AuthenticationServiceException("ApiMessages.JWTTokenExpired");
            }
        });

        scopes.forEach(role -> {
            if (!roles.contains(role)) {
                throw new AuthenticationServiceException("ApiMessages.JWTTokenExpired");
            }
        });

        return Optional.ofNullable(SecurityUserDetails.build(user, claims.getId(), token, scopes, JwtTokenFactory.getClaims(user.getUsername(), scopes, user.getUsername()), claims.getExpiration().toInstant()));
    }

    @Override
    @Transactional
    public void logout(HttpServletRequest request) {
        final String jwtToken = tokenExtractor.extract(request);
        final String jti = jwtUtil.getJti(jwtToken);
        accessTokenRepository.deleteAccessTokenByJti(jti);
    }

}
