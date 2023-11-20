package com.tgd.trip.global.oauth2.dto;

import lombok.Getter;

public enum ProviderType {
    GOOGLE("google"),
    NAVER("naver"),

    KAKAO("kakao");

    @Getter
    private final String provider;


    ProviderType(String provider){this.provider = provider;}
}
