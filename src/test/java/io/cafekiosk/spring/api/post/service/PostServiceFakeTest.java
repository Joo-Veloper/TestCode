package io.cafekiosk.spring.api.post.service;

import io.cafekiosk.spring.api.mock.FakePostRepository;
import io.cafekiosk.spring.api.mock.FakeUserRepository;
import io.cafekiosk.spring.api.post.dto.PostCreateDto;
import io.cafekiosk.spring.api.post.dto.PostUpdateDto;
import io.cafekiosk.spring.domain.post.entity.Post;
import io.cafekiosk.spring.domain.user.entity.User;
import io.cafekiosk.spring.global.common.service.port.ClockHolderTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.cafekiosk.spring.api.user.dto.UserStatus.ACTIVE;
import static io.cafekiosk.spring.api.user.dto.UserStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;

class PostServiceFakeTest {
    private PostServiceImpl postService;

    @BeforeEach
    void init() {
        FakePostRepository fakePostRepository = new FakePostRepository();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        this.postService = PostServiceImpl.builder()
                .postRepository(fakePostRepository)
                .userRepository(fakeUserRepository)
                .clockHolder(new ClockHolderTest(1679530673958L))
                .build();
        User user1 = User.builder()
                .id(1L)
                .email("joo@test.com")
                .nickname("tester")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(ACTIVE)
                .lastLoginAt(0L)
                .build();
        User user2 = User.builder()
                .id(2L)
                .email("joo1@test.com")
                .nickname("tester1")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .status(PENDING)
                .lastLoginAt(0L)
                .build();
        fakeUserRepository.save(user1);
        fakeUserRepository.save(user2);
        fakePostRepository.save(Post.builder()
                .id(1L)
                .content("helloWorld")
                .createdAt(1678530673958L)
                .modifiedAt(0L)
                .writer(user1)
                .build());
    }

    @Test
    void getById는_존재하는_게시물을_내려준다() {
        //given & when
        Post result = postService.getById(1);

        //then
        assertThat(result.getContent()).isEqualTo("helloWorld");
        assertThat(result.getWriter().getEmail()).isEqualTo("joo@test.com");
    }

    @Test
    void postCreateDto_를_이용하여_게시물을_생성할_수_있다() {
        //given
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .writerId(1)
                .content("testContent")
                .build();
        //when
        Post result = postService.create(postCreateDto);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("testContent");
        assertThat(result.getCreatedAt()).isEqualTo(1679530673958L);
    }

    @Test
    void postUpdateDto_를_이용하여_게시물을_수정할_수_있다() {
        //given
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .content("updateContent")
                .build();

        //when
        postService.update(1, postUpdateDto);

        //then
        Post post = postService.getById(1);
        assertThat(post.getContent()).isEqualTo("updateContent");
        assertThat(post.getModifiedAt()).isEqualTo(1679530673958L);
    }
}