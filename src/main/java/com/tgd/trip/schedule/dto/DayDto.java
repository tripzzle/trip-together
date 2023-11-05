package com.tgd.trip.schedule.dto;

import com.tgd.trip.attraction.dto.AttractionDto;

import java.time.LocalDate;
import java.util.List;

public class DayDto {
    public record Response(LocalDate date, List<AttractionDto.Response> attractions){

    }
}
