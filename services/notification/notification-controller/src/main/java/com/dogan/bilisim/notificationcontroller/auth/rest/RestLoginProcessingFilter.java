package com.dogan.bilisim.notificationcontroller.auth.rest;


import com.dogan.bilisim.domain.auth.LoginRequest;
import com.dogan.bilisim.domain.auth.securitUser.UserPrincipal;
import com.dogan.bilisim.notificationcontroller.exception.AuthMethodNotSupportedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
public class RestLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {


    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    private final ObjectMapper objectMapper;

    public RestLoginProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler successHandler,
                                     AuthenticationFailureHandler failureHandler, ObjectMapper mapper) {
        super(defaultProcessUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            if (log.isDebugEnabled()) {
                log.debug("Authentication method not supported. Request method: " + request.getMethod());
            }
            throw new AuthMethodNotSupportedException("Authentication method not supported");
        }

        LoginRequest loginRequest;
        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        } catch (Exception e) {
            throw new AuthenticationServiceException("Invalid login request payload");
        }

        if (StringUtils.isBlank(loginRequest.getUsername()) || StringUtils.isBlank(loginRequest.getPassword())) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }

        UserPrincipal principal = new UserPrincipal(UserPrincipal.Type.USER_NAME, loginRequest.getUsername());

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, loginRequest.getPassword());
        token.setDetails(authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
