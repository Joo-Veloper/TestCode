package io.cafekiosk.spring.api.post.service.port;

import io.cafekiosk.spring.domain.post.entity.Post;

import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(long id);

    Post save(Post post);
}
