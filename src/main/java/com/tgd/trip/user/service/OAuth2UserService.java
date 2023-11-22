package com.tgd.trip.user.service;

import com.tgd.trip.jwt.JwtTokenProvider;
import com.tgd.trip.user.domain.*;
import com.tgd.trip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    //로그인 필요한 url 요청시 여기로 넘어옴
    @Override
    @Transactional
    @CrossOrigin(origins = "http://localhost:5173")
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest: {}", userRequest);

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        log.info("clientRegistration :{}", clientRegistration);
        OAuth2User oAuth2User = super.loadUser(userRequest);

        oAuth2User.getAuthorities().forEach((k) -> {
            log.info("k: {}", k);
        });

        // oauth 회원가입 강제 등록
        OAuth2UserInfo oAuth2UserInfo = null;

        if (clientRegistration.getRegistrationId().equals("google")) {
            log.info("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
            log.info("oAuth2UserInfo: {}", oAuth2UserInfo);
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            oAuth2UserInfo = new KakaoUserInfo((Map) oAuth2User.getAttributes().get("kakao_account"));
        } else {
            log.info("우리는 구글과 페이스북만 지원합니다.");
        }
        System.out.println("유저정보" + oAuth2UserInfo);


        String provider = oAuth2UserInfo.getProvider(); //google , naver, facebook etc
        String providerId = clientRegistration.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        String name = oAuth2UserInfo.getName();
        String email = oAuth2UserInfo.getEmail();

        log.info("email: {}", email);
        log.info("name: {}", name);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user = null;
        if (optionalUser.isPresent()) {
            log.info("로그인을 이미 했음, 자동회원가입이 되어있다.");
            user = optionalUser.get();
        } else {
            user = User.builder()
                    .password("githere")
                    .name(name)
                    .email(email)
                    .roles(List.of(Role.GEST.name()))
                    .providerId(providerId)
                    .provider(provider)
                    .build();
            log.info("user: {}", user);
            userRepository.save(user);
        }
        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuthAttributes authAttributes = OAuthAttributes.of(provider, providerId, attributes);
        log.info("attributes : {}", attributes);
        log.info("authAttributes : {}", authAttributes);
        return CustomOAuth2User.of(user, attributes, authAttributes);
    }

}
