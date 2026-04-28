package org.volkov.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.volkov.userservice.dto.UserDto;
import org.volkov.userservice.entity.User;
import org.volkov.userservice.service.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public HttpStatus create(@RequestBody UserDto dto) {
        userService.create(dto);
        return HttpStatus.CREATED;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> readAll() {
        return new ResponseEntity<>(userService.readAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDto> update (@RequestBody User user) {
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        userService.delete(id);
        return HttpStatus.NO_CONTENT;
    }
}
