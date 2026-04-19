package org.volkov.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.volkov.userservice.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
