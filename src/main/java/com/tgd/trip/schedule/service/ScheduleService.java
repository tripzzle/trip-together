package com.tgd.trip.schedule.service;

import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.schedule.dto.ScheduleDto;
import com.tgd.trip.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public Schedule createSchedule(Schedule schedule, ScheduleDto.Post post) {


        return scheduleRepository.save(schedule);
    }

}
