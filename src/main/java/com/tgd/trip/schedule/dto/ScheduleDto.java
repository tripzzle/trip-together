package com.tgd.trip.schedule.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class ScheduleDto {

    public record Post(LocalDate startDate, LocalDate endDate, Long userId, Boolean viewYn) {
    }

    @Builder
    public record Response(String title, String content, List<DayDto.Response> dayResponses, Boolean viewYn) {
    }
}
