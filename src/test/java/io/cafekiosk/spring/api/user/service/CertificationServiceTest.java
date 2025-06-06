package io.cafekiosk.spring.api.user.service;

import io.cafekiosk.spring.api.mock.FakeMailSender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CertificationServiceTest {
    @Test
    void 이메일과_컨텐츠가_제대로_만들여져서_보내지는지_테스트() {
        //given
        FakeMailSender fakeMailSender = new FakeMailSender();

        CertificationService certificationService = new CertificationService(fakeMailSender);
        //when
        certificationService.send("joo@test.com", 1 , "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        //then
        assertThat(fakeMailSender.email).isEqualTo("joo@test.com");
        assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
        assertThat(fakeMailSender.content).isEqualTo("Please click the following link to certify your email address: http://localhost:8080/api/users/1/verify?certificationCode=aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    }
}