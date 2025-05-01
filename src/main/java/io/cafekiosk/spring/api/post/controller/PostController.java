package io.cafekiosk.spring.api.post.controller;

import io.cafekiosk.spring.api.post.dto.PostResponseDto;
import io.cafekiosk.spring.api.post.dto.PostUpdateDto;
import io.cafekiosk.spring.api.post.service.PostService;
import io.cafekiosk.spring.api.user.mapper.UserMapper;
import io.cafekiosk.spring.domain.post.entity.PostEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시물(posts)")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable long id) {
        return ResponseEntity
                .ok()
                .body(toResponse(postService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable long id, @RequestBody PostUpdateDto postUpdateDto) {
        return ResponseEntity
                .ok()
                .body(toResponse(postService.update(id, postUpdateDto)));
    }

    public PostResponseDto toResponse(PostEntity postEntity) {
        PostResponseDto PostResponseDto = new PostResponseDto();
        PostResponseDto.setId(postEntity.getId());
        PostResponseDto.setContent(postEntity.getContent());
        PostResponseDto.setCreatedAt(postEntity.getCreatedAt());
        PostResponseDto.setModifiedAt(postEntity.getModifiedAt());
        PostResponseDto.setWriter(userMapper.userResponseDto(postEntity.getWriter()));
        return PostResponseDto;
    }
}
