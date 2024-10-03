package com.dogan.bilisim.restcontroller.gateway.base;


import com.dogan.bilisim.restservice.auth.jwt.TokenExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    private TokenExtractor tokenExtractor;

    protected String getUserPrefix(HttpServletRequest request) {
        String jwtToken = tokenExtractor.extract(request);

        String[] tokenParts = jwtToken.split("\\.");
        String body = tokenParts[1];

        return body.length() > 12 ? body.substring(body.length() - 13, body.length() - 1) : body;
    }

}
