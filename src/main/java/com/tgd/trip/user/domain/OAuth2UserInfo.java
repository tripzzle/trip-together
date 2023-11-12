package com.tgd.trip.user.domain;

public interface OAuth2UserInfo {
    String getProviderId();

    String getProvider();

    String getEmail();

    String getName();
}
