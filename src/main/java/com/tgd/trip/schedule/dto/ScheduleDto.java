package com.tgd.trip.schedule.dto;

import com.tgd.trip.user.dto.UserDto;
import lombok.Builder;

import java.util.List;

public class ScheduleDto {

    public record Post(String title, String content, Boolean viewYn, List<DayDto.Post> days, Long userId) {
    }

    public record Patch(String title, String content, Boolean viewYn, List<DayDto.Patch> days) {
    }

    @Builder
    public record Response(Long scheduleId, String title, String content, String imgUrl, Boolean viewYn, Long likeCount,
                           Long wishCount,
                           List<DayDto.Response> dayResponses,
                           UserDto.SimpleResponse user
    ) {
    }

    @Builder
    public record SimpleResponse(Long scheduleId, String title, String content, String imgUrl, Long likeCount,
                                 Long wishCount,
                                 List<DayDto.DateResponse> days,
                                 UserDto.SimpleResponse user) {
    }
}
