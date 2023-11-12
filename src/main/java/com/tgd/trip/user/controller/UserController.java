package com.tgd.trip.user.controller;

import com.tgd.trip.jwt.JwtTokenProvider;
import com.tgd.trip.security.SecurityUser;
import com.tgd.trip.user.domain.User;
import com.tgd.trip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository reop;

    // 로그인
//    @PostMapping("/login")
//    public String login(@RequestBody Map<String, String> user, @AuthenticationPrincipal SecurityUser securityUser) {
////        Member member = mapper.findByEmail(user.get("email"))
////                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 이메일입니다."));
////        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
////            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
////        }
////
////        return jwtTokenProvider.createToken(member.getEmail(), member.getRole());
//
//        Optional<User> member = reop.findByEmail(user.get("id"));
//        if(!passwordEncoder.matches(user.get("password"), member.get().getPassword())){
//            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
//        }
//        System.out.println(member.get().getRole());
//        return jwtTokenProvider.createToken(member.get().getEmail(), member.get().getRole());
//    }
    @GetMapping("/test")
    public String testApi(@AuthenticationPrincipal SecurityUser securityUser) {

        if (securityUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")))
            return "Access granted to /test for ROLE_ADMIN.";
        else
            return "Access denied. You do not have the required authority.";
    }
}
