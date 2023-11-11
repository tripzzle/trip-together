package com.tgd.trip.security;

import com.tgd.trip.user.domain.User;
import com.tgd.trip.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class SecurityUserDetailService implements UserDetailsService {

    private UserRepository repo;

    public SecurityUserDetailService(UserRepository mapper) {
        this.repo = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("loadByUserName");

        Optional<User> user = repo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " 사용자 없음");
        } else {
            return new SecurityUser(user.get());
        }
    }
}