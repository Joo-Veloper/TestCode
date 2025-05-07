package io.cafekiosk.spring.api.post.controller;

import io.cafekiosk.spring.api.post.controller.port.PostService;
import io.cafekiosk.spring.api.post.dto.PostCreateDto;
import io.cafekiosk.spring.api.post.dto.PostResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시물(posts)")
@Builder
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostCreateController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> create(@RequestBody PostCreateDto postCreateDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PostResponseDto.from(postService.create(postCreateDto)));
    }
}