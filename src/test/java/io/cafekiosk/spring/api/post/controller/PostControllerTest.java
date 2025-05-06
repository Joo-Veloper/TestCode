package io.cafekiosk.spring.api.post.controller;

import io.cafekiosk.spring.api.mock.TestContainer;
import io.cafekiosk.spring.api.post.dto.PostResponseDto;
import io.cafekiosk.spring.api.post.dto.PostUpdateDto;
import io.cafekiosk.spring.api.user.dto.UserStatus;
import io.cafekiosk.spring.domain.post.entity.Post;
import io.cafekiosk.spring.domain.user.entity.User;
import io.cafekiosk.spring.global.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class PostControllerTest {

    @Test
    void 사용자는_게시물을_단건_조회_할_수_있다() {

        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        User user = User.builder()
                .id(1L)
                .email("joo@test.com")
                .nickname("tester")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .lastLoginAt(100L)
                .build();
        testContainer.userRepository.save(user);
        testContainer.postRepository.save(Post.builder()
                .id(1L)
                .content("helloWorld")
                .writer(user)
                .createdAt(100L)
                .build());

        //when
        ResponseEntity<PostResponseDto> result = testContainer.postController.getPostById(1L);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("helloWorld");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("tester");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
    }

    @Test
    void 사용자가_존재하지_않는_게시물을_조회할_경우_에러가_난다() {

        //given
        TestContainer testContainer = TestContainer.builder().build();

        //when & then
        assertThatThrownBy(() -> {
            testContainer.postController.getPostById(1);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_게시물을_수정할_수_있다() throws Exception {// given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 200L)
                .build();
        User user = User.builder()
                .id(1L)
                .email("joo@test.com")
                .nickname("tester")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .lastLoginAt(100L)
                .build();
        testContainer.userRepository.save(user);
        testContainer.postRepository.save(Post.builder()
                .id(1L)
                .content("helloWorld")
                .writer(user)
                .createdAt(100L)
                .build());

        //when
        ResponseEntity<PostResponseDto> result = testContainer.postController.updatePost(1L, PostUpdateDto.builder()
                .content("foobar")
                .build());

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("foobar");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("tester");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
        assertThat(result.getBody().getModifiedAt()).isEqualTo(200L);
    }
}