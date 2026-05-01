package org.volkov.userservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Учебный API",
                description = "API управления пользователями",
                version = "1.0.0",
                contact = @Contact(
                        name = "Илья Волков",
                        email = "involkov2022@gmail.com"
                )
        )
)
public class OpenApiConfig {
}
