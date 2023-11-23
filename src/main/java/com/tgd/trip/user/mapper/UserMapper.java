package com.tgd.trip.user.mapper;

import com.tgd.trip.attraction.domain.AttractionBookmark;
import com.tgd.trip.attraction.dto.AttractionDto;
import com.tgd.trip.schedule.domain.ScheduleBookmark;
import com.tgd.trip.schedule.dto.DayDto;
import com.tgd.trip.schedule.dto.ScheduleDto;
import com.tgd.trip.user.domain.User;
import com.tgd.trip.user.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserDto.Response entityToResponse(User user) {
        return UserDto.Response.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .nickname(user.getNickName())
                .imgUrl(user.getImgUrl())
                .birth(user.getBirth())
                .sex(user.getSex())
                .build();
    }

    public List<ScheduleDto.SimpleResponse> entityToUserResponse(User user) {
        return user.getSchedules().stream()
                .map(schedule -> new ScheduleDto.SimpleResponse(
                        schedule.getScheduleId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getImgUrl(),
                        schedule.getWishCount(),
                        schedule.getLikeCount(),
                        schedule.getDays().stream()
                                .map(day -> new DayDto.DateResponse(day.getDayId(), day.getDate()))
                                .toList(),
                        new UserDto.SimpleResponse(
                                user.getUserId(),
                                user.getNickName(),
                                user.getImgUrl(),
                                user.getBirth(),
                                user.getSex())
                )).toList();
    }

    public List<ScheduleDto.SimpleResponse> entityToScheduleResponse(List<ScheduleBookmark> responses) {

        return responses.stream().map(response ->
                new ScheduleDto.SimpleResponse(
                        response.getSchedule().getScheduleId(),
                        response.getSchedule().getTitle(),
                        response.getSchedule().getContent(),
                        response.getSchedule().getImgUrl(),
                        response.getSchedule().getWishCount(),
                        response.getSchedule().getLikeCount(),
                        response.getSchedule().getDays().stream()
                                .map(day -> new DayDto.DateResponse(day.getDayId(), day.getDate()))
                                .toList(),
                        new UserDto.SimpleResponse(
                                response.getUser().getUserId(),
                                response.getUser().getNickName(),
                                response.getUser().getImgUrl(),
                                response.getUser().getBirth(),
                                response.getUser().getSex())
                )).toList();
    }

    public List<AttractionDto.Response> entityToAttractionResponse(List<AttractionBookmark> responses) {

        return responses.stream().map(response ->
                new AttractionDto.Response(
                        response.getAttraction()
                )).toList();
    }

}
