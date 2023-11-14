package com.tgd.trip.jwt;

import com.tgd.trip.security.LoginResponse;
import com.tgd.trip.user.service.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        loginSuccess(response, oAuth2User);
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtTokenProvider.createToken(oAuth2User.getEmail(), oAuth2User.getRole());
        response.setHeader("Authorization", "Bearer " + accessToken);
        log.info("oauth email : {} login success", oAuth2User.getEmail());
        log.info("accessToken : {}", accessToken);

        login(accessToken);
    }

    public ResponseEntity<?> login(String accessToken) {

        String jwt = accessToken;
        return ResponseEntity.ok(new LoginResponse(jwt));
    }
}