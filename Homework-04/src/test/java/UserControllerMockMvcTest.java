import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.volkov.hellospring.HelloSpring;
import org.volkov.hellospring.repository.UserEntityDto;
import org.volkov.hellospring.service.UserService;
import tools.jackson.databind.json.JsonMapper;

import java.time.OffsetDateTime;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = HelloSpring.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // Имитация сервиса
    private UserService userService;

    @Autowired
    private JsonMapper objectMapper = JsonMapper.builder().build(); // Для преобразования DTO в JSON

    @Test
    void getUserById_ShouldReturnUser() throws Exception {
        // Arrange: Настраиваем поведение mock-сервиса
        UserEntityDto dto = new UserEntityDto("Тест", "test@mail.ru", 25, OffsetDateTime.now());
        when(userService.getUserById(1L)).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Тест"));

        verify(userService).getUserById(1L);
    }

    @Test
    void createUser_ShouldCreateAndReturnUser() throws Exception {
        // Arrange
        OffsetDateTime createdAt = OffsetDateTime.now();
        UserEntityDto request = new UserEntityDto("Тест 2", "test2@mail.ru", 30, createdAt);
        UserEntityDto response = new UserEntityDto("Тест 2", "test2@mail.ru", 30, createdAt);

        when(userService.createUser(request)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
