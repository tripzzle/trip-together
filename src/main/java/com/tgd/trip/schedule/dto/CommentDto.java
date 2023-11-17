package com.tgd.trip.schedule.dto;

import com.tgd.trip.user.dto.UserDto;

import java.time.LocalDateTime;

public class CommentDto {

    public record Post(String content, Long userId) {
    }

    public record Patch(String content, Long userId) {
    }

    public record Response(Long commentId, String content, UserDto.SimpleResponse writer, LocalDateTime createdAt, LocalDateTime updatedAt) {
    }

}
