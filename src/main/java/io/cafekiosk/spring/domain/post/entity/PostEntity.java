package io.cafekiosk.spring.domain.post.entity;

import io.cafekiosk.spring.domain.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Setter
@Table(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "modified_at")
    private Long modifiedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity writer;

    public static PostEntity from(Post post) {
        PostEntity postEntity = new PostEntity();
        postEntity.id = post.getId();
        postEntity.content = post.getContent();
        postEntity.createdAt = post.getCreatedAt();
        postEntity.modifiedAt = post.getModifiedAt();
        postEntity.writer = UserEntity.from(post.getWriter());
        return postEntity;
    }

    public Post toModel() {
        return Post.builder()
                .id(id)
                .content(content)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .writer(writer.toModel())
                .build();
    }
}