package com.dogan.bilisim.service.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;


@Slf4j
@RequiredArgsConstructor
@Getter
@Service
public class JwtUtil {

    private PublicKey jwtPublicKey;
    private PrivateKey jwtPrivateKey;
    @NotNull
    private final JwtSettings jwtSettings;

    @PostConstruct
    public void init() {
        java.security.Security.addProvider(
                new org.bouncycastle.jce.provider.BouncyCastleProvider()
        );
        jwtPrivateKey =
                KeyUtil.readPrivateKey(jwtSettings.getJwtPrivateFile());
        jwtPublicKey = KeyUtil.readPublicKey(jwtSettings.getJwtPublicFile());
    }

    public String getUsername(String token) {
        return getBody(token).getSubject();
    }

    public Date getExpiration(final String token) {
        return getBody(token).getExpiration();
    }

    public Claims getBody(String token) throws ExpiredJwtException {
        return Jwts.parser().verifyWith(jwtPublicKey).build().parseSignedClaims(token).getPayload();
    }

    public boolean validateJwtToken(final String authToken) {
        try {
            getBody(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Invalid JWT: {}", e.getMessage());
        }
        return false;
    }

    public String getJti(final String token) {
        return getBody(token).getId();

    }
}
