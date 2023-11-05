package com.tgd.trip.schedule.controller;

import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.schedule.dto.ScheduleDto;
import com.tgd.trip.schedule.mapper.ScheduleMapper;
import com.tgd.trip.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    @PostMapping
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleDto.Post post) {
        Schedule schedule = scheduleService.createSchedule(post);
        return ResponseEntity.created(URI.create(String.format("api/schdule/%s", schedule.getScheduleId()))).build();
    }

    @GetMapping("{schedule-id}")
    public ResponseEntity<?> getSchedule(@PathVariable("schedule-id") Long id) {
        Schedule schedule = scheduleService.getSchedule(id);
        ScheduleDto.Response response = scheduleMapper.entityToResponse(schedule);

        return ResponseEntity.ok(response);
    }
}
