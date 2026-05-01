package org.volkov.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.volkov.userservice.dto.UserDto;
import org.volkov.userservice.entity.User;
import org.volkov.userservice.service.UserService;

import java.util.List;

@Tag(name = "Пользователи", description = "Контроллер для управления пользователями")
@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать пользователя"
    )
    @PostMapping
    public HttpStatus create(@RequestBody UserDto dto) {
        userService.create(dto);
        return HttpStatus.CREATED;
    }

    @Operation(
            summary = "Список всех пользователей",
            description = "Позволяет получить всех зарегистрированных пользователей"
    )
    @GetMapping
    public ResponseEntity<List<UserDto>> readAll() {
        return new ResponseEntity<>(userService.readAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Изменение данных пользователя",
            description = "Позволяет обновить данные пользователя"
    )
    @PutMapping
    public ResponseEntity<UserDto> update (@RequestBody User user) {
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление пользователя",
            description = "Удаляет пользователя по его id"
    )
    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        userService.delete(id);
        return HttpStatus.NO_CONTENT;
    }
}
