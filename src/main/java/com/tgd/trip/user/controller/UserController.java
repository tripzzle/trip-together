package com.tgd.trip.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgd.trip.attraction.domain.Attraction;
import com.tgd.trip.jwt.JwtTokenProvider;
import com.tgd.trip.schedule.domain.Schedule;
import com.tgd.trip.security.SecurityUser;
import com.tgd.trip.user.domain.User;
import com.tgd.trip.user.dto.SignupDto;
import com.tgd.trip.user.dto.UserDto;
import com.tgd.trip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
        try {
            Long userId = securityUser.getMember().getUserId();
            if (securityUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {            // 요청한 유저가 게스트 라면
                user = userService.getUserInfo(userId);
                System.out.println("유저 찾음" + user);
            }
            System.out.println("유저정보 반환" + user.toString());
        }catch (Exception e){
            System.out.println("문제발생!!");
            e.printStackTrace();
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

    @GetMapping("/userSchedule")
    public ResponseEntity<?> userSchedule(@AuthenticationPrincipal SecurityUser securityUser) {
        List<Schedule> userSchedule = null;
        try {
            Long userId = securityUser.getMember().getUserId();
            if (securityUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
                userSchedule = userService.userSchedule(userId);
            }
        }catch (Exception e){
            System.out.println("문제발생!!!!!!!!!!!!!!!");

        e.printStackTrace();        }
        return ResponseEntity.ok(userSchedule);
    }
    @GetMapping("/userWishSD")
    public ResponseEntity<?> userWishSD(@AuthenticationPrincipal SecurityUser securityUser) {
        List<Schedule> userWishSD = null;
        Long userId =  securityUser.getMember().getUserId();

        if (securityUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))){
            userWishSD = userService.userWishSD(userId);
        }
        return ResponseEntity.ok(userWishSD);

    }

    @GetMapping("/userWishAT")
    public ResponseEntity<?> userWishAT(@AuthenticationPrincipal SecurityUser securityUser) {
        List<Attraction> userWishAT = null;
        Long userId =  securityUser.getMember().getUserId();

        if (securityUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))){
            userWishAT = userService.userWishAT(userId);
        }
        return ResponseEntity.ok(userWishAT);
    }
}
