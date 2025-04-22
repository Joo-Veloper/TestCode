package io.cafekiosk.spring.api.post.dto;

import io.cafekiosk.spring.api.user.dto.UserResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDto {

    private Long id;
    private String content;
    private Long createdAt;
    private Long modifiedAt;
    private UserResponseDto writer;
}
