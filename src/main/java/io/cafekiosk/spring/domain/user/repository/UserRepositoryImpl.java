package io.cafekiosk.spring.domain.user.repository;

import io.cafekiosk.spring.api.user.dto.UserStatus;
import io.cafekiosk.spring.api.user.service.port.UserRepository;
import io.cafekiosk.spring.domain.user.entity.User;
import io.cafekiosk.spring.domain.user.entity.UserEntity;
import io.cafekiosk.spring.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User getById(long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Override
    public Optional<User> findById(long id) {
        return userJpaRepository.findById(id).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
        return userJpaRepository.findByIdAndStatus(id, userStatus).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
        return userJpaRepository.findByEmailAndStatus(email, userStatus).map(UserEntity::toModel);
    }

    @Override
    public User save(User user) {
        // 저장은 반대로 도메인 객체를 영속성 객체로 변환 메서드
        // user.toEntity()로 하는 것도 좋지만 domain은 Infra Layer 의 정보를 모르는 것이 좋다.
        return userJpaRepository.save(UserEntity.from(user)).toModel();
    }
}
