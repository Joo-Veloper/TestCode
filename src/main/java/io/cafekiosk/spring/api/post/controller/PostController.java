package io.cafekiosk.spring.api.post.controller;

import io.cafekiosk.spring.api.post.dto.PostResponseDto;
import io.cafekiosk.spring.api.post.dto.PostUpdateDto;
import io.cafekiosk.spring.api.post.service.PostService;
import io.cafekiosk.spring.api.user.controller.UserController;
import io.cafekiosk.spring.domain.post.entity.Post;
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
    private final UserController userController;

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

    public PostResponseDto toResponse(Post post) {
        PostResponseDto PostResponseDto = new PostResponseDto();
        PostResponseDto.setId(post.getId());
        PostResponseDto.setContent(post.getContent());
        PostResponseDto.setCreatedAt(post.getCreatedAt());
        PostResponseDto.setModifiedAt(post.getModifiedAt());
        PostResponseDto.setWriter(userController.userResponseDto(post.getWriter()));
        return PostResponseDto;
    }
}
