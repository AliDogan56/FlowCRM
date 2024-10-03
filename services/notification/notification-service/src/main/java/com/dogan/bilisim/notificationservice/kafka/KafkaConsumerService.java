package com.dogan.bilisim.notificationservice.kafka;


import com.dogan.bilisim.dto.notification.SendNotificationDTO;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class KafkaConsumerService {
    private final SimpMessagingTemplate messagingTemplate;
    private static final Log logger = LogFactory.getLog(KafkaConsumerService.class);


    @KafkaListener(topics = "notification", containerFactory = "notificationKafkaListenerContainerFactory")
    public void consume(SendNotificationDTO sendNotificationDTO) {
        logger.info("Received notification: " + sendNotificationDTO);

        String destination = "/queue/notifications/" + sendNotificationDTO.getUserPrefix();
        messagingTemplate.convertAndSend(destination, sendNotificationDTO.getMessage());
    }


}
