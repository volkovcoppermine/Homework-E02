package org.volkov.userservice.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.volkov.userservice.dto.UserDto;
import org.volkov.userservice.entity.User;
import org.volkov.userservice.repository.UserRepository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private final MessageProducer messageProducer;

    public User create(UserDto dto) {
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .age(dto.getAge())
                .createdAt(OffsetDateTime.now())
                .build();

        messageProducer.sendMessage("user-created-topic", user.getEmail());

        return userRepository.save(user);
    }

    public List<UserDto> readAll() {
        List<User> allUsers = userRepository.findAll();
        List<UserDto> result = new ArrayList<>();
        for (User u : allUsers) {
            result.add(UserDto.builder()
                    .name(u.getName())
                    .age(u.getAge())
                    .email(u.getEmail())
                    .build());
        }

        return result;
    }

    public UserDto update(User user) {
        User updatedUser = userRepository.save(user);
        return UserDto.builder()
                .name(updatedUser.getName())
                .age(updatedUser.getAge())
                .email(updatedUser.getEmail())
                .build();
    }

    public void delete(Long id) {
        User user = userRepository.getReferenceById(id);
        messageProducer.sendMessage("user-deleted-topic", user.getEmail());

        userRepository.deleteById(id);
    }
}
