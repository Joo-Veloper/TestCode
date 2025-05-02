package io.cafekiosk.spring.api.user.service;

import io.cafekiosk.spring.api.user.dto.UserCreateDto;
import io.cafekiosk.spring.api.user.dto.UserStatus;
import io.cafekiosk.spring.api.user.dto.UserUpdateDto;
import io.cafekiosk.spring.api.user.service.port.UserRepository;
import io.cafekiosk.spring.domain.user.entity.User;
import io.cafekiosk.spring.global.common.service.port.ClockHolder;
import io.cafekiosk.spring.global.common.service.port.UuidHolder;
import io.cafekiosk.spring.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CertificationService certificationService;
    private final UuidHolder uuidHolder;
    private final ClockHolder clockHolder;

    public User getByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Users", email));
    }

    public User getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Transactional
    public User create(UserCreateDto userCreateDto) {
        User user = User.from(userCreateDto, uuidHolder);
        user = userRepository.save(user);
        certificationService.send(userCreateDto.getEmail(), user.getId(), user.getCertificationCode());
        return user;
    }

    @Transactional
    public User update(long id, UserUpdateDto userUpdateDto) {
        User user = getById(id);
        user = user.update(userUpdateDto);
        user = userRepository.save(user);
        return user;
    }

    @Transactional
    public void login(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
        user = user.login(clockHolder);
        userRepository.save(user);
    }

    @Transactional
    public void verifyEmail(long id, String certificationCode) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
        user = user.certificate(certificationCode);
        userRepository.save(user);
    }
}
