package org.volkov.notificationservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.volkov.notificationservice.dto.EmailDto;
import org.volkov.notificationservice.service.EmailService;

@RestController
@AllArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping
    public HttpStatus create(@RequestBody EmailDto dto) {
        emailService.sendEmail(dto.getTo(), dto.getSubject(), dto.getMessage());
        return HttpStatus.CREATED;
    }
}
