package io.cafekiosk.spring.api.post.service;

import io.cafekiosk.spring.api.post.controller.port.PostService;
import io.cafekiosk.spring.api.post.dto.PostCreateDto;
import io.cafekiosk.spring.api.post.dto.PostUpdateDto;
import io.cafekiosk.spring.api.post.service.port.PostRepository;
import io.cafekiosk.spring.api.user.service.port.UserRepository;
import io.cafekiosk.spring.domain.post.entity.Post;
import io.cafekiosk.spring.domain.user.entity.User;
import io.cafekiosk.spring.global.common.service.port.ClockHolder;
import io.cafekiosk.spring.global.exception.ResourceNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;

    public Post getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public Post create(PostCreateDto postCreateDto) {
        User writer = userRepository.getById(postCreateDto.getWriterId());
        Post post = Post.from(writer, postCreateDto, clockHolder);
        return postRepository.save(post);
    }

    public Post update(long id, PostUpdateDto postUpdateDto) {
        Post post = getById(id);
        post = post.update(postUpdateDto, clockHolder);
        return postRepository.save(post);
    }
}
