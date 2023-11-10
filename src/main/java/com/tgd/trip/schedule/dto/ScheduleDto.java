package com.tgd.trip.schedule.dto;

import lombok.Builder;

import java.util.List;

public class ScheduleDto {

    public record Post(String title,
                       String content,
                       Boolean viewYn,
                       String imgUrl,
                       List<DayDto.Post> days
    ) {
    }

    public record Patch(String title,
                        String content,
                        Boolean viewYn,
                        String imgUrl,
                        List<DayDto.Patch> days
    ) {
    }

    @Builder
    public record Response(String title, String content, List<DayDto.Response> dayResponses, Boolean viewYn) {
    }

    public record SimpleResponse(String title, String content, String imgUrl, List<DayDto.DateResponse> days) {
    }
}
