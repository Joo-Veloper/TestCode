package io.cafekiosk.spring.api.user.dto;

import io.cafekiosk.spring.domain.user.entity.User;
import org.junit.jupiter.api.Test;

import static io.cafekiosk.spring.api.user.dto.UserStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

class MyProfileResponseTest {

    @Test
    void User로_응답을_생성할_수_있다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("joo@test.com")
                .nickname("tester")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        //when
        MyProfileResponse myProfileResponse = MyProfileResponse.from(user);

        //then
        assertThat(myProfileResponse.getId()).isEqualTo(1);
        assertThat(myProfileResponse.getEmail()).isEqualTo("joo@test.com");
        assertThat(myProfileResponse.getNickname()).isEqualTo("tester");
        assertThat(myProfileResponse.getStatus()).isEqualTo(ACTIVE);
        assertThat(myProfileResponse.getLastLoginAt()).isEqualTo(100L);
    }

}