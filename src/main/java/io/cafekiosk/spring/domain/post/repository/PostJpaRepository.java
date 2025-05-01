package io.cafekiosk.spring.domain.post.repository;

import io.cafekiosk.spring.domain.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {
}
