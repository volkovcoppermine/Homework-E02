package org.volkov.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import org.volkov.userservice.entity.UserEntity;
import org.volkov.userservice.repository.UserEntityDto;
import org.volkov.userservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserEntityDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserEntityDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserEntityDto createUser(@RequestBody UserEntityDto request) {
        UserEntityDto created = userService.createUser(request);
        kafkaTemplate.send("user-events", new UserEvent("CREATE", created.getEmail()));
        return created;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserEntityDto updateUser(@PathVariable Long id, @RequestBody UserEntityDto request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        UserEntityDto found = userService.getUserById(id);
        userService.deleteUser(id);
        kafkaTemplate.send("user-events", new UserEvent("DELETE", found.getEmail()));
    }
}
