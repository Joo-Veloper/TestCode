package io.cafekiosk.spring.api.post.service;

import io.cafekiosk.spring.api.post.dto.PostCreateDto;
import io.cafekiosk.spring.api.post.dto.PostUpdateDto;
import io.cafekiosk.spring.api.user.service.UserService;
import io.cafekiosk.spring.domain.post.entity.Post;
import io.cafekiosk.spring.domain.post.repository.PostRepository;
import io.cafekiosk.spring.domain.user.entity.User;
import io.cafekiosk.spring.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public Post getPostById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public Post createPost(PostCreateDto postCreateDto) {
        User user = userService.getByIdOrElseThrow(postCreateDto.getWriterId());
        Post post = new Post();
        post.setWriter(user);
        post.setContent(postCreateDto.getContent());
        post.setCreatedAt(Clock.systemUTC().millis());
        return postRepository.save(post);
    }

    public Post updatePost(long id, PostUpdateDto postUpdateDto) {
        Post postEntity = getPostById(id);
        postEntity.setContent(postUpdateDto.getContent());
        postEntity.setModifiedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }
}
