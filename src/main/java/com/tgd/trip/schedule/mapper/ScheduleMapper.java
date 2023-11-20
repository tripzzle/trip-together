package com.tgd.trip.schedule.mapper;

import com.tgd.trip.attraction.dto.AttractionDto;
import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.schedule.dto.*;
import com.tgd.trip.user.dto.UserDto;
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
                .wishCount(schedule.getWishCount())
                .likeCount(schedule.getLikeCount())
                .dayResponses(schedule.getDays().stream()
                        .map(day -> new DayDto.Response(
                                day.getDayId(),
                                day.getDate(),
                                day.getDayAttractions().stream()
                                        .map(dayAttraction -> new AttractionDto.Response(dayAttraction.getAttraction()))
                                        .toList()))
                        .toList())
                .user(UserDto.SimpleResponse.builder()
                        .userId(schedule.getUser().getUserId())
                        .nickname(schedule.getUser().getNickName())
                        .imgUrl(schedule.getUser().getImgUrl())
                        .birth(schedule.getUser().getBirth())
                        .sex(schedule.getUser().getSex())
                        .build())
                .build();
    }

    public List<ScheduleDto.SimpleResponse> simpleResponses(List<Schedule> schedules) {
        return schedules.stream()
                .map(schedule -> ScheduleDto.SimpleResponse.builder()
                        .scheduleId(schedule.getScheduleId())
                        .title(schedule.getTitle())
                        .content(schedule.getContent())
                        .imgUrl(schedule.getImgUrl())
                        .wishCount(schedule.getWishCount())
                        .likeCount(schedule.getLikeCount())
                        .days(schedule.getDays().stream()
                                .map(day -> new DayDto.DateResponse(day.getDayId(), day.getDate()))
                                .toList())
                        .user(UserDto.SimpleResponse.builder()
                                .userId(schedule.getUser().getUserId())
                                .nickname(schedule.getUser().getNickName())
                                .imgUrl(schedule.getUser().getImgUrl())
                                .birth(schedule.getUser().getBirth())
                                .sex(schedule.getUser().getSex())
                                .build()
                        ).build())
                .toList();
    }

    public List<CommentDto.Response> entityToCommentResponse(Schedule schedule) {
        return schedule.getComments().stream()
                .map(comment -> new CommentDto.Response(
                        comment.getCommentId(),
                        comment.getContent(),
                        UserDto.SimpleResponse.builder()
                                .userId(comment.getUser().getUserId())
                                .nickname(comment.getUser().getNickName())
                                .imgUrl(comment.getUser().getImgUrl())
                                .birth(comment.getUser().getBirth())
                                .sex(comment.getUser().getSex())
                                .build(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()))
                .toList();
    }
}
