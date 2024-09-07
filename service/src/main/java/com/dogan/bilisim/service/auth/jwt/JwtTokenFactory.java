package com.dogan.bilisim.service.auth.jwt;


import com.dogan.bilisim.domain.auth.token.AccessTokenType;
import com.dogan.bilisim.domain.auth.token.JwtToken;
import com.dogan.bilisim.domain.auth.securitUser.SecurityUser;
import com.dogan.bilisim.domain.auth.token.TokenDetails;
import com.dogan.bilisim.service.auth.util.JwtSettings;
import com.dogan.bilisim.service.auth.util.JwtUtil;
import com.dogan.bilisim.service.user.AppUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JwtTokenFactory {

    public static final String SCOPES = "scopes";
    public static final String USER_ID = "userId";
    @NotNull
    private final JwtSettings jwtSettings;
    @NotNull
    private final JwtUtil jwtUtil;

    private final AppUserService appUserService;

    public TokenDetails generateToken(final String subject, final List<String> roles, final String userId) {
        Claims claims = getClaims(subject, roles, userId);

        Instant currentTime = Instant.ofEpochSecond(Instant.now().getEpochSecond(), 0);
        Instant expireDate = currentTime.plusSeconds(jwtSettings.getTokenExpirationTime());

        final String jti = UUID.randomUUID().toString();
        String token = Jwts.builder().claims(claims).issuer(jwtSettings.getTokenIssuer()).issuedAt(Date.from(currentTime)).expiration(Date.from(expireDate)).signWith(jwtUtil.getJwtPrivateKey()).id(jti).compact();
        return new TokenDetails(token, AccessTokenType.BEARER, jti, userId, expireDate);
    }

    public Boolean isExpired(final String token) {
        return jwtUtil.getExpiration(token).toInstant().compareTo(Instant.ofEpochSecond(Instant.now().getEpochSecond(), 0)) < 0;
    }

    public static Claims getClaims(String subject, List<String> roles, String userId) {
        return Jwts.claims().subject(subject).add(SCOPES, roles).add(USER_ID, userId).build();
    }

    @Transactional
    public JwtToken generateToken(SecurityUser user) {
        return generateToken(user.getUserPrincipal().getValue(), user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()), user.getUserPrincipal().getValue());
    }
}
