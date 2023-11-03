package com.tgd.trip.schedule.dto;

import java.time.LocalDate;

public class ScheduleDto {

    public record Post(LocalDate startDate, LocalDate endDate, Long userId) {
    }
}
