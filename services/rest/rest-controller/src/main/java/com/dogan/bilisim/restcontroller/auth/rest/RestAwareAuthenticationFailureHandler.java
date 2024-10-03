package com.dogan.bilisim.restcontroller.auth.rest;

import com.dogan.bilisim.restcontroller.exception.FlowCrmErrorResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component("defaultAuthenticationFailureHandler")
public class RestAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final FlowCrmErrorResponseHandler errorResponseHandler;

    @Autowired
    public RestAwareAuthenticationFailureHandler(FlowCrmErrorResponseHandler errorResponseHandler) {
        this.errorResponseHandler = errorResponseHandler;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) {
        errorResponseHandler.handle(e, response);
    }
}
