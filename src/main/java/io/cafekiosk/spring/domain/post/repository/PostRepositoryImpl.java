package io.cafekiosk.spring.domain.post.repository;

import io.cafekiosk.spring.api.post.service.port.PostRepository;
import io.cafekiosk.spring.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Optional<Post> findById(long id) {
        return postJpaRepository.findById(id);
    }

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(post);
    }
}
