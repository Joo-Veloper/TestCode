package io.cafekiosk.spring.domain.user.repository;

import io.cafekiosk.spring.api.user.dto.UserStatus;
import io.cafekiosk.spring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);
}
