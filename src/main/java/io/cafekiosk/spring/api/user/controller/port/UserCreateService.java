package io.cafekiosk.spring.api.user.controller.port;

import io.cafekiosk.spring.api.user.dto.UserCreateDto;
import io.cafekiosk.spring.domain.user.entity.User;

public interface UserCreateService {

    User create(UserCreateDto userCreateDto);

}
