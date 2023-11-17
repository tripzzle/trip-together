package com.tgd.trip.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "스케줄이 존재하지 않습니다."),
    DAY_NOT_FOUND(HttpStatus.NOT_FOUND, "일자가 존재하지 않습니다."),
    ATTRACTION_NOT_FOUND(HttpStatus.NOT_FOUND, "관광지가 존재하지 않습니다."),
    TOO_MANY_LIKES(HttpStatus.TOO_MANY_REQUESTS, "이미 좋아요를 했습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
