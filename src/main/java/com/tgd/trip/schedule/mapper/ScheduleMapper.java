package com.tgd.trip.schedule.mapper;

import com.tgd.trip.attraction.dto.AttractionDto;
import com.tgd.trip.schedule.domain.DayAttraction;
import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.schedule.dto.DayDto;
import com.tgd.trip.schedule.dto.ScheduleDto;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    public ScheduleDto.Response entityToResponse(Schedule schedule) {
        return ScheduleDto.Response.builder()
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .viewYn(schedule.getViewYn())
                .dayResponses(schedule.getDays().stream().map(
                        day -> new DayDto.Response(day.getDate(), day.getDayAttractions().stream().map(dayAttraction ->  new AttractionDto.Response(dayAttraction.getAttraction())).toList())
                ).toList())
                .build();
    }
}
