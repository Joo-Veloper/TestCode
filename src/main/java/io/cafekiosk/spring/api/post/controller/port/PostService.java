package io.cafekiosk.spring.api.post.controller.port;

import io.cafekiosk.spring.api.post.dto.PostCreateDto;
import io.cafekiosk.spring.api.post.dto.PostUpdateDto;
import io.cafekiosk.spring.domain.post.entity.Post;

public interface PostService {

    Post getById(long id);

    Post create(PostCreateDto postCreateDto);

    Post update(long id, PostUpdateDto postUpdateDto);
}
