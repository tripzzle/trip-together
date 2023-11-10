package com.tgd.trip.schedule.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class ScheduleDto {

    public record Post(String title,
                       String content,
                       Boolean viewYn,
                       String imgUrl,
                       List<DayDto.Post> days
                       ) {
    }

    @Builder
    public record Response(String title, String content, List<DayDto.Response> dayResponses, Boolean viewYn) {
    }
}
