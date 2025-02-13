package com.tgd.trip.user.service;


import com.tgd.trip.exception.CustomException;
import com.tgd.trip.exception.ExceptionCode;
import com.tgd.trip.user.domain.*;
import lombok.Builder;
import lombok.Getter;
import java.util.Map;

@Getter
public class OAuthAttributes {
    private final String providerId;
    private final OAuth2UserInfo oAuth2UserInfo;

    @Builder
    public OAuthAttributes(String providerId, OAuth2UserInfo oAuth2UserInfo) {
        this.providerId = providerId;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    public static OAuthAttributes of(String provider,
                                     String providerId,
                                     Map<String, Object> attributes) {
        System.out.println(providerId);
        if (provider.equals(ProviderType.GOOGLE.getProvider())) {
            return ofGoogle(providerId, attributes);
        }else if(provider.equals(ProviderType.KAKAO.getProvider())){
            return ofKakao(providerId, attributes);
        }else if(provider.equals(ProviderType.NAVER.getProvider())){
            return ofNaver(providerId, attributes);
        }
        throw new CustomException(ExceptionCode.PROVIDER_NOT_FOUND);
    }

    private static OAuthAttributes ofGoogle(String providerId,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .providerId(providerId)
                .oAuth2UserInfo(new GoogleUserInfo(attributes))
                .build();
    }

    private static OAuthAttributes ofKakao(String providerId,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .providerId(providerId)
                .oAuth2UserInfo(new KakaoUserInfo(attributes))
                .build();
    }

    private static OAuthAttributes ofNaver(String providerId,
                                           Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .providerId(providerId)
                .oAuth2UserInfo(new KakaoUserInfo(attributes))
                .build();
    }
}
