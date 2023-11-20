package com.tgd.trip.global.oauth2.dto;

import com.tgd.trip.global.oauth2.dto.ProviderType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class SocialLoginRequest {
    @NotNull
    private ProviderType userType;
    @NotNull
    private String code;
}