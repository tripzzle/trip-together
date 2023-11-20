package com.tgd.trip.jwt;

import com.tgd.trip.global.oauth2.dto.CustomOAuth2User;
import com.tgd.trip.user.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

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

        Cookie cookie = new Cookie("Authorization", accessToken);

        // 쿠키 설정
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setSecure(true); // HTTPS를 사용할 때만 쿠키가 전송되도록 설정
        response.addCookie(cookie); // 쿠키를 응답에 추가

        log.info("cookie : {}", cookie);
        log.info("oauth email : {} login success", oAuth2User.getUserId());
        log.info("oauth role : {}", oAuth2User.getRoles());
        log.info("accessToken 여기까진 온다 : {}", accessToken);

        String  redirectUrl = "http://localhost:5173/login";

        log.info("Url : {}", redirectUrl);

        response.sendRedirect(redirectUrl);
    }
}
