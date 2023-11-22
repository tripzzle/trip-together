package com.tgd.trip.user.mapper;

import com.tgd.trip.user.domain.User;
import com.tgd.trip.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto.Response entityToResponse(User user) {
        return UserDto.Response.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .nickname(user.getNickName())
                .imgUrl(user.getImgUrl())
                .birth(user.getBirth())
                .sex(user.getSex())
                .build();
    }
}
