//package com.tgd.trip.global.oauth2.controller;
//
//import com.tgd.trip.global.oauth2.dto.SocialLoginRequest;
//import com.tgd.trip.global.oauth2.util.LoginResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.net.URI;
//import java.util.Map;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/oauth2")
//@Slf4j
//public class OAuth2Controller {
//    private final OAuth2UserService oAuth2UserService;
//
//    @PostMapping("/social-login")
//    public ResponseEntity<?> getUserInfo(@RequestBody Map<String, String> request) {
//        String accessToken = request.get("accessToken");
//
//        // OAuth2UserRequest를 생성하기 위해 OAuth2UserRequestFactory를 주입받아 사용
//        OAuth2UserRequestFactory userRequestFactory = ...; // OAuth2UserRequestFactory 주입
//
//        // OAuth2UserRequest를 생성하여 accessToken을 전달
//        OAuth2UserRequest userRequest = userRequestFactory.createOAuth2UserRequest("google", accessToken);
//
//        // OAuth2UserService의 loadUser 메서드를 호출하여 OAuth2User를 가져옴
//        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
//
//        // OAuth2User를 활용하여 로그인 로직 처리
//        // ...
//
//        return ResponseEntity.ok("로그인 성공");
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(
//                userService.getUser(id)
//        );
//    }
//}
