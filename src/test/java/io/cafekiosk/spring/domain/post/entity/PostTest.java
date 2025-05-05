package io.cafekiosk.spring.domain.post.entity;

import io.cafekiosk.spring.api.post.dto.PostCreateDto;
import io.cafekiosk.spring.api.post.dto.PostUpdateDto;
import io.cafekiosk.spring.domain.user.entity.User;
import io.cafekiosk.spring.global.common.service.port.ClockHolderTest;
import org.junit.jupiter.api.Test;

import static io.cafekiosk.spring.api.user.dto.UserStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    @Test
    void PostCreate로_게시물을_만들_수_있다() {
        //given
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .writerId(1)
                .content("helloWorld")
                .build();
        User writer = User.builder()
                .email("joo@test.com")
                .nickname("tester")
                .address("Seoul")
                .status(ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        //when
        Post post = Post.from(writer, postCreateDto, new ClockHolderTest(1679530673958L));

        //then
        assertThat(post.getContent()).isEqualTo("helloWorld");
        assertThat(post.getCreatedAt()).isEqualTo(1679530673958L);
        assertThat(post.getWriter().getEmail()).isEqualTo("joo@test.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("tester");
        assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    }
    @Test
    void PostUpdate로_게시물을_수정할_수_있다() {
        //given
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .content("testContent")
                .build();
        User writer = User.builder()
                .email("joo@test.com")
                .nickname("tester")
                .address("Seoul")
                .status(ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        Post post = Post.builder()
                .id(1L)
                .content("helloWorld")
                .createdAt(1678530673958L)
                .modifiedAt(0L)
                .writer(writer)
                .build();

        //when
        post = post.update(postUpdateDto, new ClockHolderTest(1679530673958L));

        //then
        assertThat(post.getContent()).isEqualTo("testContent");
        assertThat(post.getModifiedAt()).isEqualTo(1679530673958L);
    }
}