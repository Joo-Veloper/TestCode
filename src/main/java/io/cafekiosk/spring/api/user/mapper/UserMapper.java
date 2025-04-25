package io.cafekiosk.spring.api.user.mapper;

import io.cafekiosk.spring.api.user.dto.MyProfileResponse;
import io.cafekiosk.spring.api.user.dto.UserResponseDto;
import io.cafekiosk.spring.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDto userResponseDto(User userEntity) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(userEntity.getId());
        userResponseDto.setEmail(userEntity.getEmail());
        userResponseDto.setNickname(userEntity.getNickname());
        userResponseDto.setStatus(userEntity.getStatus());
        userResponseDto.setLastLoginAt(userEntity.getLastLoginAt());
        return userResponseDto;
    }

    public MyProfileResponse toMyProfileResponse(User user) {
        MyProfileResponse myProfileResponse = new MyProfileResponse();
        myProfileResponse.setId(user.getId());
        myProfileResponse.setEmail(user.getEmail());
        myProfileResponse.setNickname(user.getNickname());
        myProfileResponse.setStatus(user.getStatus());
        myProfileResponse.setAddress(user.getAddress());
        myProfileResponse.setLastLoginAt(user.getLastLoginAt());
        return myProfileResponse;
    }
}
