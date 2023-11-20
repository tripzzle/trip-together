package com.tgd.trip.global.oauth2.dto.UserInfo;

public interface OAuth2UserInfo {
    String getProviderId();

    String getProvider();

    String getEmail();

    String getName();
}
