package io.cafekiosk.spring.api.post.controller;

import io.cafekiosk.spring.api.post.controller.port.PostService;
import io.cafekiosk.spring.api.post.dto.PostResponseDto;
import io.cafekiosk.spring.api.post.dto.PostUpdateDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시물(posts)")
@Builder
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable long id) {
        return ResponseEntity
                .ok()
                .body(PostResponseDto.from(postService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable long id, @RequestBody PostUpdateDto postUpdateDto) {
        return ResponseEntity
                .ok()
                .body(PostResponseDto.from(postService.update(id, postUpdateDto)));
    }

}
