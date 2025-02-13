package com.tgd.trip.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.stream.Collectors;

public class SecurityUser extends User {
    private com.tgd.trip.user.domain.User user;

    public SecurityUser(com.tgd.trip.user.domain.User user) {
        super(user.getEmail(), user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList()));

        this.user = user;
        System.out.println("시큐리티 유저 정보 만들었다!!!!" + this.getMember().getUserId());

    }

    public com.tgd.trip.user.domain.User getMember() {
        return user;
    }
}