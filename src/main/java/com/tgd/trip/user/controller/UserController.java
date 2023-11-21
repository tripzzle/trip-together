package com.tgd.trip.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgd.trip.jwt.JwtTokenProvider;
import com.tgd.trip.security.SecurityUser;
import com.tgd.trip.user.domain.User;
import com.tgd.trip.user.dto.SignupDto;
import com.tgd.trip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @GetMapping("/signup")
    public ResponseEntity<SignupDto> signup(@AuthenticationPrincipal SecurityUser securityUser) {
        System.out.println("회원가입 요청옴");
        SignupDto tempuser = null;
        Long userId =  securityUser.getMember().getUserId();
        System.out.println(userId);
        if (securityUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_GEST"))) {            // 요청한 유저가 게스트 라면
            tempuser = userService.getSignup(userId);
        }

        return ResponseEntity.ok(tempuser);
    }

    @GetMapping("/mypage")
    public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal SecurityUser securityUser) {
        User user = null;

        Long userId =  securityUser.getMember().getUserId();
        if (securityUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {            // 요청한 유저가 게스트 라면
            user = userService.getUserInfo(userId);
            System.out.println("유저 찾음" + user);
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@AuthenticationPrincipal SecurityUser securityUser,
                                         @RequestPart("userInfo") SignupDto userInfo,
                                         @RequestPart(value = "file", required = false) MultipartFile file) {
        String newToken = null;

        if (securityUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_GEST"))) {            // 요청한 유저가 게스트 라면
            newToken = userService.postSignup(userInfo, file);
        }

        return ResponseEntity.ok(newToken);
    }

    @GetMapping("/test")
    public String testApi(@AuthenticationPrincipal SecurityUser securityUser) {

        if (securityUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")))
            return "Access granted to /test for ROLE_ADMIN.";
        else
            return "Access denied. You do not have the required authority.";
    }
}
