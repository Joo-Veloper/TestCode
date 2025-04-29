package io.cafekiosk.spring.api.user.service.port;

import io.cafekiosk.spring.api.user.dto.UserStatus;
import io.cafekiosk.spring.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(long id);

    Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

    User save(User user);
}
