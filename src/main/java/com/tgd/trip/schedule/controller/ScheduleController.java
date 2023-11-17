package com.tgd.trip.schedule.controller;

import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.schedule.dto.CommentDto;
import com.tgd.trip.schedule.dto.ScheduleDto;
import com.tgd.trip.schedule.mapper.ScheduleMapper;
import com.tgd.trip.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> createSchedule(@RequestPart ScheduleDto.Post post,
                                            @RequestParam(value = "image", required = false) MultipartFile file) {
        log.info(String.valueOf(post));
        Schedule schedule = scheduleService.createSchedule(post, file);
        return ResponseEntity.created(URI.create(String.format("api/schedule/%s", schedule.getScheduleId()))).build();
    }

    @PatchMapping("{schedule-id}")
    public ResponseEntity<?> updateSchedule(@PathVariable("schedule-id") Long id,
                                            @RequestPart ScheduleDto.Patch patch,
                                            @RequestParam(value = "image", required = false) MultipartFile file) {
        log.info(String.valueOf(patch));
        Schedule schedule = scheduleService.updateSchedule(id, patch, file);
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

    @PostMapping(value = "{schedule-id}/day/{day-id}/upload")
    public ResponseEntity<?> upload(@PathVariable("schedule-id") Long scheduleId,
                                    @PathVariable("day-id") Long dayId,
                                    @RequestParam(value = "image", required = false) List<MultipartFile> files) {
        scheduleService.createPhoto(scheduleId, dayId, files);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "{schedule-id}/wish")
    public ResponseEntity<?> createScheduleBookmark(@PathVariable("schedule-id") Long scheduleId,
                                                    @RequestParam("userId") Long userId) {
        scheduleService.createBookmark(scheduleId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "{schedule-id}/wish")
    public ResponseEntity<?> deleteScheduleBookmark(@PathVariable("schedule-id") Long scheduleId,
                                                    @RequestParam("userId") Long userId) {
        scheduleService.deleteBookmark(scheduleId, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "{schedule-id}/like")
    public ResponseEntity<?> createScheduleLike(@PathVariable("schedule-id") Long scheduleId,
                                                @RequestParam("userId") Long userId) {
        scheduleService.createLike(scheduleId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "{schedule-id}/comment")
    public ResponseEntity<?> createComment(@PathVariable("schedule-id") Long scheduleId,
                                           @RequestBody CommentDto.Post post) {
        scheduleService.createComment(scheduleId, post);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(value = "{schedule-id}/comment/{comment-id}")
    public ResponseEntity<?> updateComment(@PathVariable("schedule-id") Long scheduleId,
                                           @PathVariable("comment-id") Long commentId,
                                           @RequestBody CommentDto.Patch patch) {
        scheduleService.updateComment(scheduleId, commentId, patch);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "{schedule-id}/comment")
    public ResponseEntity<?> getComments(@PathVariable("schedule-id") Long scheduleId) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        List<CommentDto.Response> responses = scheduleMapper.entityToCommentResponse(schedule);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping(value = "{schedule-id}/comment/{comment-id}")
    public ResponseEntity<?> deleteComment(@PathVariable("schedule-id") Long scheduleId,
                                           @PathVariable("comment-id") Long commentId) {
        scheduleService.deleteComment(scheduleId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
