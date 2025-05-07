package io.cafekiosk.spring.api.post.controller;

import io.cafekiosk.spring.api.mock.TestContainer;
import io.cafekiosk.spring.api.post.dto.PostCreateDto;
import io.cafekiosk.spring.api.post.dto.PostResponseDto;
import io.cafekiosk.spring.api.user.dto.UserStatus;
import io.cafekiosk.spring.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class PostCreateControllerTest {


    @Test
    void 사용자는_회원_가입을_할_수_있고_회원가입된_사용자는_PENDING_상태이다() {
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
                .build()
        );
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .writerId(1)
                .content("helloWorld")
                .build();

        //when
        ResponseEntity<PostResponseDto> result = testContainer.postCreateController.create(postCreateDto);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("helloWorld");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("tester");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1678530673958L);
    }
}