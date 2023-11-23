package com.tgd.trip.schedule.dto;

import com.tgd.trip.user.dto.UserDto;
import lombok.Builder;

import java.util.List;

public class ScheduleBookmarkDto {
    @Builder
    public record SimpleResponse(Long scheduleBookmarkId, UserDto.SimpleResponse user, ScheduleDto.SimpleResponse schedule) {
    }
}
