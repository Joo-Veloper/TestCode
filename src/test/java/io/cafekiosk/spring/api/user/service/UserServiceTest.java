package io.cafekiosk.spring.api.user.service;

import io.cafekiosk.spring.api.user.dto.UserCreateDto;
import io.cafekiosk.spring.api.user.dto.UserUpdateDto;
import io.cafekiosk.spring.domain.user.entity.User;
import io.cafekiosk.spring.global.exception.CertificationCodeNotMatchedException;
import io.cafekiosk.spring.global.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static io.cafekiosk.spring.api.user.dto.UserStatus.ACTIVE;
import static io.cafekiosk.spring.api.user.dto.UserStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        //given
        String email = "joo@test.com";
        //when
        User result = userService.getByEmail(email);
        //then
        assertThat(result.getNickname()).isEqualTo("tester");
    }

    @Test
    void getByEmail은_PENDING_상태인_유저를_찾아올_수_없다() {
        //given
        String email = "joo1@test.com";
        //when & then
        assertThatThrownBy(() -> {
            userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById는_ACTIVE_상태인_유저를_찾아올_수_있다() {
        //given & when
        User result = userService.getById(1);
        //then
        assertThat(result.getNickname()).isEqualTo("tester");
    }

    @Test
    void getById는_PENDING_상태인_유저를_찾아올_수_없다() {
        assertThatThrownBy(() -> {
            userService.getById(2);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userCreateDto_를_이용하여_유저를_생성할_수_있다() {
        //given
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .email("joo@test.com")
                .address("Seoul")
                .nickname("tester")
                .build();
        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        //when
        User result = userService.create(userCreateDto);
        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(PENDING);
//        assertThat(result.getCertificationCode()).isEqualTo("나중수정");
    }

    @Test
    void userUpdateDto_를_이용하여_유저를_수정할_수_있다() {
        //given
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .nickname("tester1")
                .address("Inchon")
                .build();

        //when
        userService.update(1, userUpdateDto);

        //then
        User user = userService.getById(1);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getNickname()).isEqualTo("tester1");
        assertThat(user.getAddress()).isEqualTo("Inchon");
    }

    @Test
    void user를_login_시키면_마지막_로그인_시간이_변경된다() {
        //given & when
        userService.login(1);

        //then
        User user = userService.getById(1);
        assertThat(user.getLastLoginAt()).isGreaterThan(0L);
//        assertThat(result.getCertificationCode()).isEqualTo("나중수정");
    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
        //given & when
        userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

        //then
        User user = userService.getById(2);
        assertThat(user.getStatus()).isEqualTo(ACTIVE);
    }

    @Test
    void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
        assertThatThrownBy(() -> {
            userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}