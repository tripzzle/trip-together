package com.tgd.trip.user.service;

import com.tgd.trip.user.domain.Role;
import com.tgd.trip.user.domain.User;
import com.tgd.trip.util.AuthorityUtils;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private final String email;
    private final Role role;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes,
                            String nameAttributeKey,
                            String email,
                            Role role) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.role = role;
    }

    public static CustomOAuth2User of(User user, Map<String, Object> attributes) {
        return new CustomOAuth2User(
                AuthorityUtils.getAuthorities(List.of(user.getRole().toString())),
                attributes,
                user.getProviderId(),
                user.getEmail(),
                user.getRole());
    }
}