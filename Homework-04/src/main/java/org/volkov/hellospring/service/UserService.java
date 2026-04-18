package org.volkov.hellospring.service;

import org.springframework.transaction.annotation.Transactional;
import org.volkov.hellospring.repository.UserEntityDto;

import java.util.List;

public interface UserService {
    List<UserEntityDto> getAllUsers();

    UserEntityDto getUserById(Long id);

    UserEntityDto createUser(UserEntityDto request);

    @Transactional
    UserEntityDto updateUser(Long id, UserEntityDto request);

    void deleteUser(Long id);
}
