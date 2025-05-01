package io.cafekiosk.spring.api.user.mapper;

import io.cafekiosk.spring.api.user.dto.MyProfileResponse;
import io.cafekiosk.spring.api.user.dto.UserResponseDto;
import io.cafekiosk.spring.domain.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDto userResponseDto(UserEntity userEntity) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(userEntity.getId());
        userResponseDto.setEmail(userEntity.getEmail());
        userResponseDto.setNickname(userEntity.getNickname());
        userResponseDto.setStatus(userEntity.getStatus());
        userResponseDto.setLastLoginAt(userEntity.getLastLoginAt());
        return userResponseDto;
    }

    public MyProfileResponse toMyProfileResponse(UserEntity userEntity) {
        MyProfileResponse myProfileResponse = new MyProfileResponse();
        myProfileResponse.setId(userEntity.getId());
        myProfileResponse.setEmail(userEntity.getEmail());
        myProfileResponse.setNickname(userEntity.getNickname());
        myProfileResponse.setStatus(userEntity.getStatus());
        myProfileResponse.setAddress(userEntity.getAddress());
        myProfileResponse.setLastLoginAt(userEntity.getLastLoginAt());
        return myProfileResponse;
    }
}
