package com.tgd.trip.user.service;


import com.tgd.trip.exception.CustomException;
import com.tgd.trip.exception.ExceptionCode;
import com.tgd.trip.user.domain.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

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

//    public User toEntity(OAuth2UserInfo oAuth2UserInfo,
//                         String username,
//                         String provider,
//                         PasswordEncoder passwordEncoder) {
//        return new User(
//                passwordEncoder.encode("OAUTH" + UUID.randomUUID()),
//                username,
//                oAuth2UserInfo.getEmail(),
//                Collections.singletonList("ROLE_USER"),
//                provider,
//                oAuth2UserInfo.getProviderId()
//        );
//    }
}
