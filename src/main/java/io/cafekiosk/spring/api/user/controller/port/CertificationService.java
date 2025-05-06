package io.cafekiosk.spring.api.user.controller.port;

public interface CertificationService {

    void send(String email, long id, String certificationCode);
}
