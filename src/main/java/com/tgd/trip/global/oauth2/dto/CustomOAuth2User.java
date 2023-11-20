package com.tgd.trip.global.oauth2.dto;

import com.tgd.trip.global.oauth2.util.OAuthAttributes;
import com.tgd.trip.user.domain.User;
import com.tgd.trip.util.AuthorityUtils;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private final List<String> roles;
    private final Long userId;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes,
                            String nameAttributeKey,
                            Long userId,
                            List<String> roles) {
        super(authorities, attributes, nameAttributeKey);
        this.roles = roles;
        this.userId = userId;
    }

    public static CustomOAuth2User of(User user, Map<String, Object> attributes, OAuthAttributes oAuthAttributes) {
        return new CustomOAuth2User(
                AuthorityUtils.getAuthorities(user.getRoles()),
                attributes,
                oAuthAttributes.getProviderId(),
                user.getUserId(),
                user.getRoles()
                );
    }
}