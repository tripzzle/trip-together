package com.tgd.trip.user.service;

import com.tgd.trip.user.domain.User;
import com.tgd.trip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User getVerifyUser(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public void createUser(User user) {
        userRepository.save(user);
    }
}
