package com.dogan.bilisim.notificationcontroller.websocket;

import com.dogan.bilisim.dao.log.WebSocketLogRepository;
import com.dogan.bilisim.domain.log.WebSocketLog;
import com.dogan.bilisim.domain.log.WebSocketLogStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Configuration
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final WebSocketLogRepository webSocketLogRepository;

    public WebSocketEventListener(WebSocketLogRepository webSocketLogRepository) {
        this.webSocketLogRepository = webSocketLogRepository;
    }


    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        changeStatus(headerAccessor, WebSocketLogStatus.CONNECTED);
        logger.info("New WS Connection: " + headerAccessor.getSessionId());
    }

    private void changeStatus(StompHeaderAccessor headerAccessor, WebSocketLogStatus webSocketLogStatus) {
        WebSocketLog webSocketLog = webSocketLogRepository.findBySessionId(headerAccessor.getSessionId());

        if (WebSocketLogStatus.DISCONNECTED.equals(webSocketLogStatus)) {
            if (webSocketLog != null) {
                webSocketLogRepository.delete(webSocketLog);
            }
        } else {
            webSocketLog.setWebSocketLogStatus(webSocketLogStatus);
            webSocketLogRepository.save(webSocketLog);
        }

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        changeStatus(headerAccessor, WebSocketLogStatus.DISCONNECTED);

        logger.info("WS Connection has been cut: " + headerAccessor.getSessionId());
    }
}
