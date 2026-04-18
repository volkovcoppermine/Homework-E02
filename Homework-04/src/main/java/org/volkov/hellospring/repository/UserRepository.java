package org.volkov.hellospring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.volkov.hellospring.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
