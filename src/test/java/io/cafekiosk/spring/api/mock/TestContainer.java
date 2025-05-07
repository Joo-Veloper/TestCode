package io.cafekiosk.spring.api.mock;

import io.cafekiosk.spring.api.post.controller.PostController;
import io.cafekiosk.spring.api.post.controller.PostCreateController;
import io.cafekiosk.spring.api.post.controller.port.PostService;
import io.cafekiosk.spring.api.post.service.PostServiceImpl;
import io.cafekiosk.spring.api.post.service.port.PostRepository;
import io.cafekiosk.spring.api.user.controller.UserController;
import io.cafekiosk.spring.api.user.controller.UserCreateController;
import io.cafekiosk.spring.api.user.service.CertificationService;
import io.cafekiosk.spring.api.user.service.UserServiceImpl;
import io.cafekiosk.spring.api.user.service.port.MailSender;
import io.cafekiosk.spring.api.user.service.port.UserRepository;
import io.cafekiosk.spring.global.common.service.port.ClockHolder;
import io.cafekiosk.spring.global.common.service.port.UuidHolder;
import lombok.Builder;

public class TestContainer {

    public final MailSender mailSender;
    public final UserRepository userRepository;
    public final PostRepository postRepository;
    public final PostService postService;
    public final CertificationService certificationService;
    public final UserController userController;
    public final UserCreateController userCreateController;
    public final PostController postController;
    public final PostCreateController postCreateController;

    @Builder
    public TestContainer(ClockHolder clockHolder, UuidHolder uuidHolder) {
        this.mailSender = new FakeMailSender();
        this.userRepository = new FakeUserRepository();
        this.postRepository = new FakePostRepository();
        this.postService = PostServiceImpl.builder()
                .postRepository(this.postRepository)
                .userRepository(this.userRepository)
                .clockHolder(clockHolder)
                .build();
        this.certificationService = new CertificationService(this.mailSender);
        UserServiceImpl userService = UserServiceImpl.builder()
                .uuidHolder(uuidHolder)
                .clockHolder(clockHolder)
                .userRepository(this.userRepository)
                .certificationService(this.certificationService)
                .build();
        this.userController = UserController.builder()
                .userService(userService)
                .build();
        this.userCreateController = UserCreateController.builder()
                .userService(userService)
                .build();
        this.postController = PostController.builder()
                .postService(postService)
                .build();
        this.postCreateController = PostCreateController.builder()
                .postService(postService)
                .build();
    }
}
