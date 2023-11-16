package com.tgd.trip.user.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserStatus {
    ACTIVE("활성화된 계정")
    ,DELETE("삭제된 계정")
    
    ;
    
    private final String status;
    
}
