package com.tgd.trip.global.oauth2.dto.UserInfo;

import lombok.Data;

import java.util.Map;

@Data
public class KakaoUserInfo implements OAuth2UserInfo {

    private Map<String, Object> kakaoAccount;

    public KakaoUserInfo(Map<String, Object> attributes ) {
        this.kakaoAccount = attributes;
    }

    @Override
    public String getProviderId() {
        return String.valueOf(kakaoAccount.get("id"));
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return String.valueOf(kakaoAccount.get("email"));
    }

    @Override
    public String getName() {
        return String.valueOf(kakaoAccount.get("profile_nickname"));
    }
}
