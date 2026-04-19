package org.volkov.notificationservice.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.volkov.notificationservice.model.UserEvent;
import org.volkov.notificationservice.service.EmailService;

@Service
public class NotificationListener {

    private EmailService emailService;

    @KafkaListener(topics = "user-events")
    public void handleUserEvent(UserEvent event) {
        String subject = "Уведомление";
        String text = "CREATE".equals(event.getOperation())
                ? "Здравствуйте! Ваш аккаунт на сайте был успешно создан."
                : "Здравствуйте! Ваш аккаунт был удалён.";
        emailService.sendEmail(event.getEmail(), subject, text);
    }
}
