package org.volkov.notificationservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.volkov.notificationservice.dto.MessageWithType;

@Service
public class KafkaEmailConsumer {
    private static final String SUBJECT = "Ваш аккаунт";
    private static final String ACCOUNT_CREATED = "Здравствуйте! Ваш аккаунт был создан.";
    private static final String ACCOUNT_DELETED = "Здравствуйте! Ваш аккаунт был удалён.";

    private final EmailService emailService;

    @Value(value = "${kafka.consumer.topic}")
    private String topic;

    public KafkaEmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${kafka.consumer.topic}")
    public void listenCreate(MessageWithType message) {
        String text = message.getType().equals("Create") ? ACCOUNT_CREATED : ACCOUNT_DELETED;
        emailService.sendEmail(message.getContent(), SUBJECT, text);
    }
}
