package io.cafekiosk.spring.domain.post.entity;

import io.cafekiosk.spring.api.post.dto.PostCreateDto;
import io.cafekiosk.spring.domain.user.entity.User;
import org.junit.jupiter.api.Test;

import static io.cafekiosk.spring.api.user.dto.UserStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    @Test
    void PostCreate로_게시물을_만들_수_있다() {
        //given
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .writerId(1)
                .content("helloworld")
                .build();
        User writer = User.builder()
                .email("joo@test.com")
                .nickname("tester")
                .address("Seoul")
                .status(ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        //when
        Post post = Post.from(writer, postCreateDto);

        //then
        assertThat(post.getContent()).isEqualTo("helloworld");
        assertThat(post.getWriter().getEmail()).isEqualTo("joo@test.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("tester");
        assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    }
}