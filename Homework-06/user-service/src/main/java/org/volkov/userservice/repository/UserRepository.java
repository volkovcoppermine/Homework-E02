package org.volkov.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.volkov.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
