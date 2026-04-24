package org.volkov.notificationservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaEmailConsumer {
    private final EmailService emailService;

    public KafkaEmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "user-created-topic")
    public void listenCreate(String message) {
        emailService.sendEmail(message, "Ваш аккаунт", "Здравствуйте! Ваш аккаунт был создан.");
    }

    @KafkaListener(topics = "user-deleted-topic")
    public void listenDelete(String message) {
        emailService.sendEmail(message, "Ваш аккаунт", "Здравствуйте! Ваш аккаунт был удалён.");
    }
}
