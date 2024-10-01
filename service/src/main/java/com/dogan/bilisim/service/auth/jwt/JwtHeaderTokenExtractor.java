package com.dogan.bilisim.service.auth.jwt;

import com.dogan.bilisim.domain.exception.FlowCrmErrorCode;
import com.dogan.bilisim.service.auth.exception.FlowCrmSecurityException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component("jwtHeaderTokenExtractor")
public class JwtHeaderTokenExtractor implements TokenExtractor {
    public static final String HEADER_PREFIX = "Bearer ";
    @Value("${flowcrm.token.header.name}")
    private String tokenHeaderName;

    @Override
    public String extract(HttpServletRequest request) {
        String header = request.getHeader(tokenHeaderName);
        if (!StringUtils.hasText(header)) {
            throw new FlowCrmSecurityException(FlowCrmErrorCode.AUTHENTICATION, "ApiMessages.AuthorizationHeaderCannotBeBlank");
        }

        if (header.length() < HEADER_PREFIX.length()) {
            throw new FlowCrmSecurityException(FlowCrmErrorCode.AUTHENTICATION, "ApiMessages.InvalidAuthorizationHeaderSize");
        }

        return header.substring(HEADER_PREFIX.length());
    }

    public String extract(String header) {
        if (!StringUtils.hasText(header)) {
            throw new FlowCrmSecurityException(FlowCrmErrorCode.AUTHENTICATION, "ApiMessages.AuthorizationHeaderCannotBeBlank");
        }

        if (header.length() < HEADER_PREFIX.length()) {
            throw new FlowCrmSecurityException(FlowCrmErrorCode.AUTHENTICATION, "ApiMessages.InvalidAuthorizationHeaderSize");
        }

        return header.substring(HEADER_PREFIX.length());
    }
}
