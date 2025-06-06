package io.cafekiosk.spring.api.user.controller;

import io.cafekiosk.spring.api.user.controller.port.UserService;
import io.cafekiosk.spring.api.user.dto.UserCreateDto;
import io.cafekiosk.spring.api.user.dto.UserResponseDto;
import io.cafekiosk.spring.domain.user.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저(users)")
@Builder
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserCreateController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateDto userCreateDto) {
        User user = userService.create(userCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponseDto.from(user));
    }
}
