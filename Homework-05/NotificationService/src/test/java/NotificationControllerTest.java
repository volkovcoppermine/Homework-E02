import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.volkov.notificationservice.controller.NotificationController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class NotificationControllerTest {
    @Autowired
    private NotificationController controller;

    @MockitoBean
    private JavaMailSender mailSender;

    @Test
    void testSendEmail() {
        controller.sendEmail("test@mail.com", "CREATE");
        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}