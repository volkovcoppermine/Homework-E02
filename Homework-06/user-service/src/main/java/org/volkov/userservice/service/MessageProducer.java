package org.volkov.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.volkov.userservice.dto.MessageWithType;

@Service
public class MessageProducer {
    @Autowired
    private KafkaTemplate<String, MessageWithType> kafkaTemplate;

    public void sendMessage(String topic, MessageWithType message) {
        kafkaTemplate.send(topic, message);
    }
}
