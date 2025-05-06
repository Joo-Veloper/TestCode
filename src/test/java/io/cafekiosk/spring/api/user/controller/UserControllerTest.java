package io.cafekiosk.spring.api.user.controller;

import io.cafekiosk.spring.api.mock.TestContainer;
import io.cafekiosk.spring.api.user.dto.MyProfileResponse;
import io.cafekiosk.spring.api.user.dto.UserResponseDto;
import io.cafekiosk.spring.api.user.dto.UserStatus;
import io.cafekiosk.spring.api.user.dto.UserUpdateDto;
import io.cafekiosk.spring.domain.user.entity.User;
import io.cafekiosk.spring.global.exception.CertificationCodeNotMatchedException;
import io.cafekiosk.spring.global.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class UserControllerTest {

    @Test
    void 사용자는_특정_유저의_개인정보는_소거된채_전달_받을_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("joo@test.com")
                .nickname("tester")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .lastLoginAt(100L)
                .build());

        //when
        ResponseEntity<UserResponseDto> result = testContainer.userController.getUserById(1);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("joo@test.com");
        assertThat(result.getBody().getNickname()).isEqualTo("tester");
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(100);
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();


        //when & then
        assertThatThrownBy(() -> {
            testContainer.userController.getUserById(1);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() {

        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("joo@test.com")
                .nickname("tester")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .lastLoginAt(100L)
                .build());

        //when
        ResponseEntity<Void> result = testContainer.userController.verifyEmail(1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(302));
        assertThat(testContainer.userRepository.getById(1).getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() {

        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("joo@test.com")
                .nickname("tester")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .lastLoginAt(100L)
                .build());

        //when & then
        assertThatThrownBy(() -> {
            testContainer.userController.verifyEmail(1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }

    @Test
    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() {

        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1678530673958L)
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("joo@test.com")
                .nickname("tester")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .lastLoginAt(100L)
                .build());

        //when
        ResponseEntity<MyProfileResponse> result = testContainer.userController.getMyInfo("joo@test.com");

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("joo@test.com");
        assertThat(result.getBody().getNickname()).isEqualTo("tester");
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(1678530673958L);
        assertThat(result.getBody().getAddress()).isEqualTo("Seoul");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_내_정보를_수정할_수_있다() {

        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("joo@test.com")
                .nickname("tester")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .lastLoginAt(100L)
                .build());

        //when
        ResponseEntity<MyProfileResponse> result = testContainer.userController.updateMyInfo("joo@test.com", UserUpdateDto.builder()
                .address("Pangyo")
                .nickname("tester2")
                .build());

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("joo@test.com");
        assertThat(result.getBody().getNickname()).isEqualTo("tester2");
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(100);
        assertThat(result.getBody().getAddress()).isEqualTo("Pangyo");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}