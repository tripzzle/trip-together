package com.tgd.trip.schedule.mapper;

import com.tgd.trip.attraction.dto.AttractionDto;
import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.schedule.dto.DayDto;
import com.tgd.trip.schedule.dto.ScheduleDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleMapper {

    public ScheduleDto.Response entityToResponse(Schedule schedule) {
        return ScheduleDto.Response.builder()
                .scheduleId(schedule.getScheduleId())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .viewYn(schedule.getViewYn())
                .dayResponses(
                        schedule.getDays().stream()
                                .map(day -> new DayDto.Response(
                                        day.getDayId(),
                                        day.getDate(),
                                        day.getDayAttractions().stream()
                                                .map(dayAttraction -> new AttractionDto.Response(dayAttraction.getAttraction()))
                                                .toList()))
                                .toList())
                .build();
    }

    public List<ScheduleDto.SimpleResponse> simpleResponses(List<Schedule> schedules) {
        return schedules.stream()
                .map(schedule -> new ScheduleDto.SimpleResponse(
                        schedule.getScheduleId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getImgUrl(),
                        schedule.getDays().stream()
                                .map(day -> new DayDto.DateResponse(day.getDayId(), day.getDate()))
                                .toList()))
                .toList();
    }

}
