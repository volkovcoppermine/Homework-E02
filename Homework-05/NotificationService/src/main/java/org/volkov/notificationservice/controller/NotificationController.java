package org.volkov.notificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.volkov.notificationservice.service.EmailService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/email")
    public void sendEmail(@RequestParam String email,
                          @RequestParam String operation) {
        String subject = "Уведомление о пользователе";
        String text = "CREATE".equals(operation)
                ? "Здравствуйте! Ваш аккаунт на сайте был успешно создан."
                : "Здравствуйте! Ваш аккаунт был удалён.";
        emailService.sendEmail(email, subject, text);
    }
}
