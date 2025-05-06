package io.cafekiosk.spring.api.user.controller.port;

import io.cafekiosk.spring.domain.user.entity.User;

public interface UserReadService {
    User getByEmail(String email);

    User getById(long id);
}
