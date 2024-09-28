package com.dogan.bilisim.interceptor;

import com.dogan.bilisim.dao.log.RequestLogRepository;
import com.dogan.bilisim.domain.log.RequestLog;
import com.dogan.bilisim.service.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {
    private final RequestLogRepository requestLogRepository;
    private final JwtUtil jwtUtil;


    public RequestLoggingInterceptor(RequestLogRepository requestLogRepository, JwtUtil jwtUtil) {
        this.requestLogRepository = requestLogRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        RequestLog log = new RequestLog();
        log.setMethod(request.getMethod());
        log.setUrl(request.getRequestURI());
        log.setIpAddress(request.getRemoteAddr());

        String userAgent = request.getHeader("User-Agent");
        String operatingSystem = extractOperatingSystem(userAgent);
        log.setOperatingSystem(operatingSystem);

        String token = getJwtFromRequest(request);
        if (token != null) {
            String userId = extractUserIdFromToken(token);
            log.setUserId(userId);
        }

        requestLogRepository.save(log);


        return true;
    }

    private String extractOperatingSystem(String userAgent) {
        if (userAgent == null) return "Unknown";
        if (userAgent.contains("Windows")) return "Windows";
        if (userAgent.contains("Mac")) return "Mac";
        if (userAgent.contains("X11")) return "Unix";
        if (userAgent.contains("Android")) return "Android";
        if (userAgent.contains("iPhone")) return "iOS";
        return "Unknown";
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String extractUserIdFromToken(String token) {
        Claims claims = jwtUtil.getBody(token);
        return claims.getSubject();
    }
}
