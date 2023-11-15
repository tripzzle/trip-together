package com.tgd.trip.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {
    private com.tgd.trip.user.domain.User user;

    public SecurityUser(com.tgd.trip.user.domain.User user) {
        super(user.getEmail(), user.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_"+user.getRoles()));
        this.user = user;
    }

    public com.tgd.trip.user.domain.User getMember() {
        return user;
    }
}