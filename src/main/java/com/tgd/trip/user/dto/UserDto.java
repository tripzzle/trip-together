package com.tgd.trip.user.dto;

import lombok.Builder;

import java.time.LocalDate;

public class UserDto {

    @Builder
    public record SimpleResponse(Long userId, String nickname, String imgUrl, LocalDate birth, Boolean sex) {
    }
}
