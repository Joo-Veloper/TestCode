package io.cafekiosk.spring.domain.post.repository;

import io.cafekiosk.spring.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
}
