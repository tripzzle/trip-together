package com.tgd.trip.schedule.mapper;

import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.schedule.dto.ScheduleDto;

public class ScheduleMapper {

    public Schedule toEntity(ScheduleDto scheduleDto) {
        return new Schedule();
    }
}
