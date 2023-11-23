package com.tgd.trip.schedule.dto;

import com.tgd.trip.attraction.dto.AttractionDto;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class DayDto {

    public record Post(LocalDate date, List<DayAttractionDto> dayAttractions) {
    }

    public record Patch(LocalDate date, List<DayAttractionDto> dayAttractions) {
    }
    @Builder

    public record Response(Long dayId, LocalDate date, List<AttractionDto.Response> attractions) {
    }

    public record DateResponse(Long dayId, LocalDate date) {
    }
}
