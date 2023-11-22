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
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),
    DIFFERENT_USER(HttpStatus.FORBIDDEN, "같은 사용자가 아닙니다."),
    TOO_MANY_LIKES(HttpStatus.CONFLICT, "이미 좋아요를 했습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다."),
    ALREADY_BOOKMARKED_SCHEDULE(HttpStatus.CONFLICT, "이미 북마크된 스케줄입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
