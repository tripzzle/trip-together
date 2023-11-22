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
import com.tgd.trip.user.mapper.UserMapper;
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
    private final UserMapper userMapper;
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
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal SecurityUser securityUser) {
        UserDto.Response response = null;
        try {
            Long userId = securityUser.getMember().getUserId();
            if (securityUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {            // 요청한 유저가 게스트 라면
                User user  = userService.getUserInfo(userId);
                response  = userMapper.entityToResponse(user);
                System.out.println("유저 찾음" + user);
            }
        }catch (Exception e){
            System.out.println("문제발생!!");
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
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

    @PatchMapping("/userupdate")
    public ResponseEntity<?> userUpdate(@AuthenticationPrincipal SecurityUser securityUser,
                                        @RequestPart UserDto.Patch patch,
                                        @RequestPart(value = "file", required = false) MultipartFile file){
        System.out.println("유저업데이트 컨트롤러 요청" + patch);

        User user = userService.userUpdate(securityUser.getMember(), patch, file);

        UserDto.Response response = userMapper.entityToResponse(user);

        return ResponseEntity.ok(response);

    }

    @PatchMapping("/userdelete")
    public ResponseEntity<?> userDelete(@AuthenticationPrincipal SecurityUser securityUser,
                                        @RequestPart UserDto.Patch delete){
        if(securityUser.getMember().getUserId() == delete.userId()){
            userService.userDelete(securityUser.getMember());
        }

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/userSchedule")
    public ResponseEntity<?> userSchedule(@AuthenticationPrincipal SecurityUser securityUser) {
        List<Schedule> userSchedule = null;
        try {
            Long userId = securityUser.getMember().getUserId();
            userSchedule = userService.userSchedule(userId);
        }catch (Exception e){
            System.out.println("문제발생!!!!!!!!!!!!!!!");

        e.printStackTrace();
        }
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
