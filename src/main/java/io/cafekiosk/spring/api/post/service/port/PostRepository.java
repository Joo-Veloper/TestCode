package io.cafekiosk.spring.api.post.service.port;

import io.cafekiosk.spring.domain.post.entity.PostEntity;

import java.util.Optional;

public interface PostRepository {
    Optional<PostEntity> findById(long id);

    PostEntity save(PostEntity postEntity);
}
