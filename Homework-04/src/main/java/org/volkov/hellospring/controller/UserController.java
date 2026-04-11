package org.volkov.hellospring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import org.volkov.hellospring.repository.UserEntityDto;
import org.volkov.hellospring.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/users — получить всех пользователей
    @GetMapping
    public List<UserEntityDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET /api/users/{id} — получить пользователя по ID
    @GetMapping("/{id}")
    public UserEntityDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // POST /api/users — создать пользователя
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserEntityDto createUser(@RequestBody UserEntityDto request) {
        return userService.createUser(request);
    }

    // PUT /api/users/{id} — обновить пользователя
    @PutMapping("/{id}")
    public UserEntityDto updateUser(@PathVariable Long id, @RequestBody UserEntityDto request) {
        return userService.updateUser(id, request);
    }

    // DELETE /api/users/{id} — удалить пользователя
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}