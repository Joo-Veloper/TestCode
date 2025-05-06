package io.cafekiosk.spring.api.user.controller.port;

public interface AuthenticationService {

    void login(long id);

    void verifyEmail(long id, String certificationCode);
}
