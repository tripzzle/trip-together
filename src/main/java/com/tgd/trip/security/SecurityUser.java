package com.tgd.trip.security;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

@Slf4j
public class SecurityUser extends User {
    private com.tgd.trip.user.domain.User user;

    public SecurityUser(com.tgd.trip.user.domain.User user) {
        super(user.getEmail(), user.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_" + user.getRoles().stream().findFirst().get()));
        this.user = user;
    }

    public com.tgd.trip.user.domain.User getMember() {
        return user;
    }
}