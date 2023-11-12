package com.tgd.trip.user.domain;

import lombok.Data;

import java.util.Map;

@Data
public class NaverUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes; // oauth2User.getAttributes()

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {

        return String.valueOf(attributes.get("email"));
    }

    @Override
    public String getName() {

        return String.valueOf(attributes.get("name"));

    }
}
