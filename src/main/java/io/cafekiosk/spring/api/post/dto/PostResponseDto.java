package io.cafekiosk.spring.api.post.dto;

import io.cafekiosk.spring.api.user.dto.UserResponseDto;
import io.cafekiosk.spring.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponseDto {

    private Long id;
    private String content;
    private Long createdAt;
    private Long modifiedAt;
    private UserResponseDto writer;

    public static PostResponseDto from(Post post) {

        return PostResponseDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .writer(UserResponseDto.from(post.getWriter()))
                .build();
    }
}
