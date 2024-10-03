package com.dogan.bilisim.restservice.kafka;


import com.dogan.bilisim.dto.notification.SendNotificationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private static final String TOPIC = "notification";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(SendNotificationDTO sendNotificationDTO) throws JsonProcessingException {
        new Thread(() -> {
            kafkaTemplate.send(TOPIC, sendNotificationDTO);
            logger.info("Successfully sent message: " + sendNotificationDTO);
        }).start();
    }
}
