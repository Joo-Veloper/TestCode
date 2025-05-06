package io.cafekiosk.spring.api.user.controller.port;

import io.cafekiosk.spring.api.user.dto.UserUpdateDto;
import io.cafekiosk.spring.domain.user.entity.User;

public interface UserUpdateService {
    User update(long id, UserUpdateDto userUpdateDto);

    void login(long id);

    void verifyEmail(long id, String certificationCode);
}
