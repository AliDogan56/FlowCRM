package com.dogan.bilisim.notification;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest request) {
        String destination = "/queue/notifications/" + request.getUserPrefix();
        messagingTemplate.convertAndSend(destination, request.getMessage());
        return ResponseEntity.ok("Message sent successfully");
    }


}
