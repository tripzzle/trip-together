package com.tgd.trip.jwt;


import com.tgd.trip.user.service.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("OAuth login 성공");
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        loginSuccess(request, response, oAuth2User);
    }

    private void loginSuccess(HttpServletRequest request, HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtTokenProvider.createToken(oAuth2User.getUserId(), oAuth2User.getRoles());

        log.info("oauth email : {} login success", oAuth2User.getUserId());
        log.info("oauth role : {}", oAuth2User.getRoles());
        log.info("accessToken 여기까진 온다 : {}", accessToken);

        response.sendRedirect(createURI(accessToken));
    }

    private String createURI(String accessToken) {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(5173)
                .path("login")
                .queryParam("Authorization", accessToken)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
    }
}
