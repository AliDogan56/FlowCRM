package com.dogan.bilisim.notificationcontroller.auth.rest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import ua_parser.Client;
import ua_parser.Parser;

import java.io.Serializable;

@Data
public class RestAuthenticationDetails implements Serializable {

    private final String clientAddress;
    private final Client userAgent;

    public RestAuthenticationDetails(HttpServletRequest request) {
        this.clientAddress = getClientIP(request);
        this.userAgent = getUserAgent(request);
    }

    private static String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    private static Client getUserAgent(HttpServletRequest request) {
        try {
            Parser uaParser = new Parser();
            return uaParser.parse(request.getHeader("User-Agent"));
        } catch (Exception e) {
            return new Client(null, null, null);
        }
    }
}
