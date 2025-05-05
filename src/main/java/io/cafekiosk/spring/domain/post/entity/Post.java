package io.cafekiosk.spring.domain.post.entity;

import io.cafekiosk.spring.api.post.dto.PostCreateDto;
import io.cafekiosk.spring.api.post.dto.PostUpdateDto;
import io.cafekiosk.spring.domain.user.entity.User;
import io.cafekiosk.spring.global.common.service.port.ClockHolder;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {
    private final Long id;
    private final String content;
    private final Long createdAt;
    private final Long modifiedAt;
    private final User writer;

    @Builder
    public Post(Long id, String content, Long createdAt, Long modifiedAt, User writer) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.writer = writer;
    }


    public static Post from(User writer, PostCreateDto postCreateDto, ClockHolder clockHolder) {
        return Post.builder()
                .content(postCreateDto.getContent())
                .writer(writer)
                .createdAt(clockHolder.mills())
                .build();
    }

    public Post update(PostUpdateDto postUpdateDto, ClockHolder clockHolder) {
        return Post.builder()
                .id(id)
                .content(postUpdateDto.getContent())
                .createdAt(createdAt)
                .modifiedAt(clockHolder.mills())
                .writer(writer)
                .build();
    }
}
