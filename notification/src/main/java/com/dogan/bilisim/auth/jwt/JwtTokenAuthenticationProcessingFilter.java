package com.dogan.bilisim.auth.jwt;


import com.dogan.bilisim.domain.auth.token.RawAccessJwtToken;
import com.dogan.bilisim.domain.exception.FlowCrmErrorCode;
import com.dogan.bilisim.service.auth.exception.FlowCrmSecurityException;
import com.dogan.bilisim.service.auth.jwt.TokenExtractor;
import com.dogan.bilisim.service.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

public class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private final AuthenticationFailureHandler failureHandler;
    private final TokenExtractor tokenExtractor;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtTokenAuthenticationProcessingFilter(AuthenticationFailureHandler failureHandler,
                                                  TokenExtractor tokenExtractor, RequestMatcher requestMatcher, JwtUtil jwtUtil) {
        super(requestMatcher);
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        final String jwtToken = tokenExtractor.extract(request);
        final Claims claims;
        try {
            claims = jwtUtil.getBody(jwtToken);
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException e) {
            throw new FlowCrmSecurityException(FlowCrmErrorCode.AUTHENTICATION, e.getMessage());
        }

        RawAccessJwtToken token = new RawAccessJwtToken(jwtToken, claims.getId(), claims.getExpiration().toInstant());
        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
