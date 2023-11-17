package com.tgd.trip.user.dto;

public class UserDto {

    public record SimpleResponse(Long userId, String nickname, String imgUrl){}
}
