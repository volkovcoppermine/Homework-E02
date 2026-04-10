package org.volkov.hellotest.service;

import org.volkov.hellotest.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(UserEntity user);

    Optional<UserEntity> getUserById(long id);

    List<UserEntity> getAllUsers();

    void updateUser(UserEntity user);

    void deleteUser(UserEntity user);
}
