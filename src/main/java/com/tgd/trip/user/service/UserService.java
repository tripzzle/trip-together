package com.tgd.trip.user.service;

import com.tgd.trip.global.exception.CustomException;
import com.tgd.trip.global.exception.ErrorCode;
import com.tgd.trip.user.domain.User;
import com.tgd.trip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User getVerifyUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public User getVerifyUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public void createUser(User user) {
        userRepository.save(user);
    }
}
