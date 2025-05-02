package io.cafekiosk.spring.api.post.dto;

import io.cafekiosk.spring.domain.post.entity.Post;
import io.cafekiosk.spring.domain.user.entity.User;
import org.junit.jupiter.api.Test;

import static io.cafekiosk.spring.api.user.dto.UserStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

class PostResponseDtoTest {

    @Test
    void Post로_응답을_생성할_수_있다() {
        //given
        Post post = Post.builder()
                .content("helloworld")
                .writer(User.builder()
                        .email("joo@test.com")
                        .nickname("tester")
                        .address("Seoul")
                        .status(ACTIVE)
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                        .build())
                .build();
        //when
        PostResponseDto postResponseDto = PostResponseDto.from(post);

        //then
        assertThat(postResponseDto.getContent()).isEqualTo("helloworld");
        assertThat(postResponseDto.getWriter().getEmail()).isEqualTo("joo@test.com");
        assertThat(postResponseDto.getWriter().getNickname()).isEqualTo("tester");
        assertThat(postResponseDto.getWriter().getStatus()).isEqualTo(ACTIVE);

    }

}