package com.tgd.trip.user.domain;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public enum Role {
    USER("일반유저"),
    ADMIN("어드민"),
    GEST("처음 접속한 유저");

    private final String role;
}
