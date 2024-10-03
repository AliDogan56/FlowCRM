package com.dogan.bilisim.notificationcontroller.auth.rest;

import com.dogan.bilisim.dao.auth.AccessTokenRepository;
import com.dogan.bilisim.domain.auth.securitUser.SecurityUser;
import com.dogan.bilisim.domain.auth.token.AccessToken;
import com.dogan.bilisim.domain.auth.token.JwtToken;
import com.dogan.bilisim.notificationcontroller.auth.jwt.RefreshTokenRepository;
import com.dogan.bilisim.notificationservice.auth.jwt.JwtTokenFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component("defaultAuthenticationSuccessHandler")
public class RestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper mapper;
    private final JwtTokenFactory tokenFactory;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenRepository accessTokenRepository;

    @Autowired
    public RestAwareAuthenticationSuccessHandler(final ObjectMapper mapper, final JwtTokenFactory tokenFactory, final RefreshTokenRepository refreshTokenRepository, AccessTokenRepository accessTokenRepository) {
        this.mapper = mapper;
        this.tokenFactory = tokenFactory;
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessTokenRepository = accessTokenRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        JwtToken jwtToken = tokenFactory.generateToken(securityUser);
        JwtToken refreshToken = refreshTokenRepository.requestRefreshToken(securityUser);
        persistAccessToken(securityUser, jwtToken, refreshToken);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", jwtToken.getToken());
        tokenMap.put("refreshToken", refreshToken.getToken());

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");
        mapper.writeValue(response.getWriter(), tokenMap);

        clearAuthenticationAttributes(request);
    }

    private void persistAccessToken(SecurityUser securityUser, JwtToken token, JwtToken refreshToken) {
        final AccessToken accessToken = new AccessToken();
        accessToken.setJti(token.getTokenId());
        accessToken.setRefreshTokenJti(refreshToken.getTokenId());
        accessToken.setUserId(securityUser.getUserPrincipal().getValue());
        accessToken.setExpireDate(token.getExpireDate());
        accessTokenRepository.save(accessToken);
    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process..
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
