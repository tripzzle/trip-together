package com.tgd.trip.schedule.controller;

import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.schedule.dto.ScheduleDto;
import com.tgd.trip.schedule.mapper.ScheduleMapper;
import com.tgd.trip.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
@Slf4j
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    @PostMapping
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleDto.Post post) {
        log.info(String.valueOf(post));
        Schedule schedule = scheduleService.createSchedule(post);
        return ResponseEntity.created(URI.create(String.format("api/schdule/%s", schedule.getScheduleId()))).build();
    }

    @PatchMapping("{schedule-id}")
    public ResponseEntity<?> updateSchedule(@PathVariable("schedule-id") Long id,
                                            @RequestBody ScheduleDto.Patch patch) {
        log.info(String.valueOf(patch));
        Schedule schedule = scheduleService.updateSchedule(id,patch);
        ScheduleDto.Response response = scheduleMapper.entityToResponse(schedule);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{schedule-id}")
    public ResponseEntity<?> getSchedule(@PathVariable("schedule-id") Long id) {
        Schedule schedule = scheduleService.getSchedule(id);
        ScheduleDto.Response response = scheduleMapper.entityToResponse(schedule);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getSchedules(@RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) String sort,
                                          @PageableDefault(page = 1, size = 20) Pageable pageable) {
        List<Schedule> schedules = scheduleService.getSchedules(keyword, sort, pageable);
        List<ScheduleDto.SimpleResponse> responses = scheduleMapper.simpleResponses(schedules);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("{schedule-id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable("schedule-id") Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
