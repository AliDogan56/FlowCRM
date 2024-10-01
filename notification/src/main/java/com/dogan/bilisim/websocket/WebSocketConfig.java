package com.dogan.bilisim.websocket;

import com.dogan.bilisim.dao.log.WebSocketLogRepository;
import com.dogan.bilisim.domain.auth.securitUser.SecurityUserDetails;
import com.dogan.bilisim.domain.log.WebSocketLog;
import com.dogan.bilisim.domain.log.WebSocketLogStatus;
import com.dogan.bilisim.service.auth.AuthenticationService;
import com.dogan.bilisim.service.auth.jwt.JwtHeaderTokenExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final AuthenticationService authenticationService;

    private final WebSocketLogRepository webSocketLogRepository;
    private final JwtHeaderTokenExtractor tokenExtractor;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);


    public WebSocketConfig(AuthenticationService authenticationService, WebSocketLogRepository webSocketLogRepository, JwtHeaderTokenExtractor tokenExtractor) {
        this.authenticationService = authenticationService;
        this.webSocketLogRepository = webSocketLogRepository;
        this.tokenExtractor = tokenExtractor;
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000").withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> authHeader = accessor.getNativeHeader("Authorization");

                    if (authHeader != null && !authHeader.isEmpty()) {
                        String authToken = authHeader.get(0);
                        String extractedToken = tokenExtractor.extract(authToken);

                        Optional<SecurityUserDetails> optionalSecurityUserDetails = authenticationService.findByToken(extractedToken);

                        if (optionalSecurityUserDetails.isPresent()) {
                            SecurityUserDetails securityUserDetails = optionalSecurityUserDetails.orElseThrow(() -> new RuntimeException("User not found"));
                            saveWebSocketLog(securityUserDetails, accessor, extractedToken);

                            logger.info("The User has been validated for New WS Connection: " + securityUserDetails.getUsername());
                        } else {
                            logger.warn("Invalid Token: " + authToken);
                        }
                    } else {
                        logger.warn("Authorization header not found.");
                    }
                }

                return message;
            }
        });
    }

    private void saveWebSocketLog(SecurityUserDetails securityUserDetails, StompHeaderAccessor accessor, String extractedToken) {
        String jwtBody = extractedToken.split("\\.")[1];

        WebSocketLog webSocketLog = new WebSocketLog();
        webSocketLog.setWebSocketLogStatus(WebSocketLogStatus.PRE_CONNECT);
        webSocketLog.setUserId(securityUserDetails.getId());
        webSocketLog.setSessionId(accessor.getSessionId());
        webSocketLog.setUserPrefix(jwtBody.substring(jwtBody.length() - 13, jwtBody.length() - 1));
        webSocketLogRepository.save(webSocketLog);
    }
}
