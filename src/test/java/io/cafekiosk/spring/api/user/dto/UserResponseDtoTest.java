package io.cafekiosk.spring.api.user.dto;

import io.cafekiosk.spring.domain.user.entity.User;
import org.junit.jupiter.api.Test;

import static io.cafekiosk.spring.api.user.dto.UserStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

class UserResponseDtoTest {

    @Test
    void User로_응답을_생성할_수_있다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("joo@test.com")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        //when
        UserResponseDto userResponseDto = UserResponseDto.from(user);

        //then
        assertThat(userResponseDto.getId()).isEqualTo(1);
        assertThat(userResponseDto.getEmail()).isEqualTo("joo@test.com");
        assertThat(userResponseDto.getStatus()).isEqualTo(ACTIVE);
        assertThat(userResponseDto.getLastLoginAt()).isEqualTo(100L);

    }
}