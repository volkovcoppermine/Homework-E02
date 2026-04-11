package org.volkov.hellospring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.volkov.hellospring.entity.UserEntity;
import org.volkov.hellospring.repository.UserEntityDto;
import org.volkov.hellospring.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Получить всех пользователей
    public List<UserEntityDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Получить пользователя по ID
    public UserEntityDto getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }

    // Создать пользователя
    public UserEntityDto createUser(UserEntityDto request) {
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return convertToDto(userRepository.save(user));
    }

    // Обновить пользователя
    @Transactional
    public UserEntityDto updateUser(Long id, UserEntityDto request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return convertToDto(user);
    }

    // Удалить пользователя
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    // Вспомогательный метод для преобразования сущности в DTO
    private UserEntityDto convertToDto(UserEntity user) {
        return new UserEntityDto(user.getName(), user.getEmail(), user.getAge(), user.getCreated_at());
    }
}
