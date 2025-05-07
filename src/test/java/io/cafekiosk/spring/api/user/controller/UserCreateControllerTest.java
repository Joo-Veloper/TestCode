package io.cafekiosk.spring.api.user.controller;

import io.cafekiosk.spring.api.mock.TestContainer;
import io.cafekiosk.spring.api.user.dto.UserCreateDto;
import io.cafekiosk.spring.api.user.dto.UserResponseDto;
import io.cafekiosk.spring.api.user.dto.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateControllerTest {

    @Test
    void 사용자는_회원_가입을_할_수_있고_회원가입된_사용자는_PENDING_상태이다() throws Exception {

        // given
        TestContainer testContainer = TestContainer.builder()
                .uuidHolder(() -> "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .email("joo@test.com")
                .nickname("tester")
                .address("Pangyo")
                .build();

        //when
        ResponseEntity<UserResponseDto> result = testContainer.userCreateController.create(userCreateDto);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("joo@test.com");
        assertThat(result.getBody().getNickname()).isEqualTo("tester");
        assertThat(result.getBody().getLastLoginAt()).isNull();
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(testContainer.userRepository.getById(1).getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
    }
}